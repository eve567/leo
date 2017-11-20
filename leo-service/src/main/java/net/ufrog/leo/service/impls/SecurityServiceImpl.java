package net.ufrog.leo.service.impls;

import net.ufrog.common.Logger;
import net.ufrog.common.cache.Caches;
import net.ufrog.common.dict.Dicts;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.jpqls.SecurityJpql;
import net.ufrog.leo.domain.models.ID;
import net.ufrog.leo.domain.models.Resource;
import net.ufrog.leo.domain.models.RoleResource;
import net.ufrog.leo.domain.models.User;
import net.ufrog.leo.domain.repositories.ResourceRepository;
import net.ufrog.leo.domain.repositories.RoleResourceRepository;
import net.ufrog.leo.domain.repositories.UserRepository;
import net.ufrog.leo.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限业务实现
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-04-04
 * @since 0.1
 */
@Service
@Transactional(readOnly = true)
public class SecurityServiceImpl implements SecurityService {

    private static final String CACHE_USER_RESOURCES = "user_resources_";

    /** 类型映射 */
    private static Map<String, String> typeMapping;

    /** 资源仓库 */
    private final ResourceRepository resourceRepository;

    /** 角色资源仓库 */
    private final RoleResourceRepository roleResourceRepository;

    /** 用户仓库 */
    private final UserRepository userRepository;

    /** 权限脚本 */
    private final SecurityJpql securityJpql;

    /**
     * 构造函数
     *
     * @param resourceRepository 资源仓库
     * @param roleResourceRepository 角色资源仓库
     * @param userRepository 用户仓库
     * @param securityJpql 权限脚本
     */
    @Autowired
    public SecurityServiceImpl(ResourceRepository resourceRepository, RoleResourceRepository roleResourceRepository, UserRepository userRepository, SecurityJpql securityJpql) {
        this.resourceRepository = resourceRepository;
        this.roleResourceRepository = roleResourceRepository;
        this.userRepository = userRepository;
        this.securityJpql = securityJpql;
    }

    @Override
    public Resource findResourceById(String resourceId) {
        return resourceRepository.findOne(resourceId);
    }

    @Override
    public Resource findResourceByTypeAndReferenceId(String type, String referenceId) {
        return resourceRepository.findByTypeAndReferenceId(type, referenceId);
    }

    @Override
    public <T extends ID> List<T> filter(List<T> lResource, String userId) {
        User user = userRepository.findOne(userId);
        if (!Strings.equals(User.Type.ROOT, user.getType()) && lResource != null && lResource.size() > 0) {
            List<String> lAllowed = getAllowedResource(userId, getType(lResource.get(0).getClass()));
            return lResource.stream().filter(resource -> lAllowed.contains(resource.getId())).collect(Collectors.toList());
        }
        return lResource;
    }

    @Override
    @Transactional
    public Resource createResource(String type, String referenceId) {
        Resource resource = new Resource();
        resource.setType(type);
        resource.setReferenceId(referenceId);
        return resourceRepository.save(resource);
    }

    @Override
    @Transactional
    public Resource deleteResource(String type, String referenceId) {
        Resource resource = resourceRepository.findByTypeAndReferenceId(type, referenceId);
        if (resource != null) {
            List<RoleResource> lRoleResource = roleResourceRepository.findByResourceId(resource.getId());
            roleResourceRepository.delete(lRoleResource);
            resourceRepository.delete(resource);
        }
        return resource;
    }

    @Override
    public void clearResourceMapping(String userId) {
        Caches.safeDelete(CACHE_USER_RESOURCES, userId);
    }

    /**
     * 读取类型
     *
     * @param type 类型
     * @return 类型
     */
    private String getType(Class<? extends ID> type) {
        String tp = getTypeMapping().get(type.getName());
        if (Strings.empty(tp)) throw new ServiceException("type '" + type.getName() + "' is not mapping.");
        return tp;
    }

    /**
     * 读取类型映射表
     *
     * @return 类型映射表
     */
    private Map<String, String> getTypeMapping() {
        if (typeMapping == null) {
            typeMapping = new HashMap<>();
            Dicts.elements(Resource.Type.class).forEach((key, val) -> typeMapping.put(val.getCode(), String.valueOf(key)));
        }
        return typeMapping;
    }

    /**
     * 读取授权资源
     *
     * @param userId 用户编号
     * @param type 类型
     * @return 授权资源列表
     */
    private List<String> getAllowedResource(String userId, String type) {
        @SuppressWarnings("unchecked") Map<String, List<String>> mResource = Caches.get(CACHE_USER_RESOURCES, userId, Map.class);
        if (mResource == null) mResource = new HashMap<>();
        if (!mResource.containsKey(type)) {
            List<RoleResource> lRoleResource = securityJpql.findRoleResourceByUserIdAndType(userId, type);
            List<String> lAll = Collections.synchronizedList(new ArrayList<>());
            List<String> lBan = Collections.synchronizedList(new ArrayList<>());

            lRoleResource.forEach(roleResource -> {
                Resource resource = resourceRepository.findOne(roleResource.getResourceId());
                if (!lAll.contains(resource.getReferenceId())) {
                    lAll.add(resource.getReferenceId());
                } if (Strings.equals(roleResource.getType(), RoleResource.Type.BAN) && !lBan.contains(resource.getReferenceId())) {
                    lBan.add(resource.getReferenceId());
                }
            });
            lAll.removeAll(lBan);
            mResource.put(type, lAll);
            Logger.info("get type '%s' resource for user: %s", type, userId);
            Caches.set(CACHE_USER_RESOURCES, userId, mResource);
        }
        return mResource.get(type);
    }
}

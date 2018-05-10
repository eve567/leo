package net.ufrog.leo.service.impls;

import net.ufrog.common.Logger;
import net.ufrog.common.data.spring.Domains;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Objects;
import net.ufrog.leo.domain.Models;
import net.ufrog.leo.domain.models.Resource;
import net.ufrog.leo.domain.models.Role;
import net.ufrog.leo.domain.models.RoleResource;
import net.ufrog.leo.domain.repositories.ResourceRepository;
import net.ufrog.leo.domain.repositories.RoleRepository;
import net.ufrog.leo.domain.repositories.RoleResourceRepository;
import net.ufrog.leo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 角色业务实现
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-10
 * @since 0.1
 */
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    /** 资源仓库 */
    private final ResourceRepository resourceRepository;

    /** 角色仓库 */
    private final RoleRepository roleRepository;

    /** 角色资源仓库 */
    private final RoleResourceRepository roleResourceRepository;

    /**
     * 构造函数
     *
     * @param resourceRepository 资源仓库
     * @param roleRepository 角色仓库
     * @param roleResourceRepository 角色资源仓库
     */
    @Autowired
    public RoleServiceImpl(ResourceRepository resourceRepository, RoleRepository roleRepository, RoleResourceRepository roleResourceRepository) {
        this.resourceRepository = resourceRepository;
        this.roleRepository = roleRepository;
        this.roleResourceRepository = roleResourceRepository;
    }

    @Override
    public Role findById(String id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Role> findByAppId(String appId) {
        return roleRepository.findByAppIdAndTypeAndStatus(appId, Role.Type.PUBLIC, Role.Status.ENABLED);
    }

    @Override
    public Page<Role> findByAppId(String appId, Integer page, Integer size) {
        Pageable pageable = Domains.pageable(page, size, Domains.sort(Sort.Direction.ASC, "name"));
        return roleRepository.findByAppIdAndTypeAndStatus(appId, Role.Type.PUBLIC, Role.Status.ENABLED, pageable);
    }

    @Override
    public List<RoleResource> findRoleResources(String roleId, String type) {
        return roleResourceRepository.findByRoleIdAndType(roleId, type);
    }

    @Override
    @Transactional
    public Role create(Role role) {
        return resourceRepository.findByTypeAndReferenceId(Resource.Type.APP, role.getAppId()).map(resource -> {
            roleRepository.save(role);
            roleResourceRepository.save(Models.newRoleResource(RoleResource.Type.ALLOW, role.getId(), resource.getId()));
            return role;
        }).orElseThrow(() -> new ServiceException("cannot find resource by type: " + Resource.Type.APP + ", reference id: " + role.getAppId()));
    }

    @Override
    @Transactional
    public Role update(Role role) {
        return roleRepository.findById(role.getId()).map(oRole -> {
            Objects.copyProperties(oRole, role, Boolean.TRUE, "id", "creator", "createTime", "updater", "updateTime");
            return roleRepository.save(oRole);
        }).orElseThrow(() -> new ServiceException("cannot find role by id: " + role.getId()));
    }

    @Override
    @Transactional
    public Role delete(String id) {
        return roleRepository.findById(id).map(role -> {
            role.setStatus(Role.Status.DISABLED);
            return roleRepository.save(role);
        }).orElseThrow(() -> new ServiceException("cannot find role by id: " + id));
    }

    @Override
    @Transactional
    public List<RoleResource> bindResources(String roleId, String type, String[] allows, String[] bans) {
        List<RoleResource> lRoleResource = findRoleResources(roleId, type);
        roleResourceRepository.deleteAll(lRoleResource);
        Logger.info("delete %s original role resource reference(s).", lRoleResource.size());

        lRoleResource = new ArrayList<>(allows.length + bans.length);
        lRoleResource.addAll(newRoleResourceList(roleId, RoleResource.Type.ALLOW, allows));
        lRoleResource.addAll(newRoleResourceList(roleId, RoleResource.Type.BAN, bans));
        return roleResourceRepository.saveAll(lRoleResource);
    }

    /**
     * 新建角色资源绑定列表
     *
     * @param roleId 角色编号
     * @param type 类型
     * @param resources 资源数组
     * @return 角色资源绑定列表
     */
    private List<RoleResource> newRoleResourceList(String roleId, String type, String[] resources) {
        return Stream.of(resources).parallel().map(resource -> Models.newRoleResource(type, roleId, resource)).collect(Collectors.toList());
    }
}

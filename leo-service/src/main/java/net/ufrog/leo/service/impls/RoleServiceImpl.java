package net.ufrog.leo.service.impls;

import net.ufrog.common.data.spring.Domains;
import net.ufrog.common.utils.Objects;
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
    private ResourceRepository resourceRepository;

    /** 角色仓库 */
    private RoleRepository roleRepository;

    /** 角色资源仓库 */
    private RoleResourceRepository roleResourceRepository;

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
    public Page<Role> findByAppId(String appId, Integer page, Integer size) {
        Pageable pageable = Domains.pageable(page, size, Domains.sort(Sort.Direction.ASC, "name"));
        return roleRepository.findByAppIdAndType(appId, Role.Type.PUBLIC, pageable);
    }

    @Override
    @Transactional
    public Role create(Role role) {
        Resource resource = resourceRepository.findByTypeAndReferenceId(Resource.Type.APP, role.getAppId());
        RoleResource roleResource = new RoleResource();

        roleRepository.save(role);
        roleResource.setType(RoleResource.Type.ALLOW);
        roleResource.setRoleId(role.getId());
        roleResource.setResourceId(resource.getId());
        roleResourceRepository.save(roleResource);
        return role;
    }

    @Override
    @Transactional
    public Role update(Role role) {
        Role oRole = roleRepository.findOne(role.getId());
        Objects.copyProperties(oRole, role, Boolean.TRUE, "id", "creator", "createTime", "updater", "updateTime");
        return roleRepository.save(oRole);
    }
}

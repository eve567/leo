package net.ufrog.leo.service.impls;

import net.ufrog.common.Logger;
import net.ufrog.common.data.spring.Domains;
import net.ufrog.common.utils.Objects;
import net.ufrog.leo.domain.Models;
import net.ufrog.leo.domain.jpqls.SecurityJpql;
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
    private ResourceRepository resourceRepository;

    /** 角色仓库 */
    private RoleRepository roleRepository;

    /** 角色资源仓库 */
    private RoleResourceRepository roleResourceRepository;

    /** 权限脚本 */
    private SecurityJpql securityJpql;

    /**
     * 构造函数
     *
     * @param resourceRepository 资源仓库
     * @param roleRepository 角色仓库
     * @param roleResourceRepository 角色资源仓库
     */
    @Autowired
    public RoleServiceImpl(ResourceRepository resourceRepository, RoleRepository roleRepository, RoleResourceRepository roleResourceRepository, SecurityJpql securityJpql) {
        this.resourceRepository = resourceRepository;
        this.roleRepository = roleRepository;
        this.roleResourceRepository = roleResourceRepository;
        this.securityJpql = securityJpql;
    }

    @Override
    public Role findById(String id) {
        return roleRepository.findOne(id);
    }

    @Override
    public List<Role> findByAppId(String appId) {
        return roleRepository.findByAppIdAndType(appId, Role.Type.PUBLIC);
    }

    @Override
    public Page<Role> findByAppId(String appId, Integer page, Integer size) {
        Pageable pageable = Domains.pageable(page, size, Domains.sort(Sort.Direction.ASC, "name"));
        return roleRepository.findByAppIdAndTypeAndStatus(appId, Role.Type.PUBLIC, Role.Status.ENABLED, pageable);
    }

    @Override
    public List<RoleResource> findRoleResources(String roleId, String type) {
        return securityJpql.findRoleResourceByRoleIdAndType(roleId, type);
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

    @Override
    @Transactional
    public Role delete(String id) {
        Role role = roleRepository.findOne(id);
        role.setStatus(Role.Status.DISABLED);
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public List<RoleResource> bindResources(String roleId, String type, String[] allows, String[] bans) {
        List<RoleResource> lRoleResource = findRoleResources(roleId, type);
        roleResourceRepository.delete(lRoleResource);
        Logger.info("delete %s original role resource reference(s).", lRoleResource.size());

        lRoleResource = new ArrayList<>(allows.length + bans.length);
        lRoleResource.addAll(newRoleResourceList(roleId, RoleResource.Type.ALLOW, allows));
        lRoleResource.addAll(newRoleResourceList(roleId, RoleResource.Type.BAN, bans));
        return roleResourceRepository.save(lRoleResource);
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

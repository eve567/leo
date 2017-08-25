package net.ufrog.leo.service;

import net.ufrog.leo.domain.models.Resource;
import net.ufrog.leo.domain.models.Role;
import net.ufrog.leo.domain.models.RoleResource;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 角色业务接口
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-10
 * @since 0.1
 */
public interface RoleService {

    /**
     * 通过账号查询角色
     *
     * @param id 角色编号
     * @return 角色
     */
    Role findById(String id);

    /**
     * 通过应用编号查询角色
     *
     * @param appId 应用编号
     * @param page 当前页码
     * @param size 分页大小
     * @return 角色分页数据
     */
    Page<Role> findByAppId(String appId, Integer page, Integer size);

    /**
     * 查询角色资源关系列表
     *
     * @param roleId 角色编号
     * @param type 资源类型
     * @return 角色资源列表
     */
    List<RoleResource> findRoleResources(String roleId, String type);

    /**
     * 创建角色
     *
     * @param role 角色对象
     * @return 持久化的角色对象
     */
    Role create(Role role);

    /**
     * 更新角色
     *
     * @param role 角色对象
     * @return 持久化的角色对象
     */
    Role update(Role role);

    /**
     * 删除角色
     *
     * @param id 角色编号
     * @return 被删除角色对象
     */
    Role delete(String id);

    /**
     * 绑定资源
     *
     * @param roleId 角色编号
     * @param type 类型
     * @param allows 允许数组
     * @param bans 禁止数组
     * @return 角色资源关系列表
     */
    List<RoleResource> bindResources(String roleId, String type, String[] allows, String[] bans);
}

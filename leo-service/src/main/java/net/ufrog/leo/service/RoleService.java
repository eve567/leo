package net.ufrog.leo.service;

import net.ufrog.leo.domain.models.Role;
import org.springframework.data.domain.Page;

/**
 * 角色业务接口
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-10
 * @since 0.1
 */
public interface RoleService {

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
}

package net.ufrog.leo.domain;

import net.ufrog.leo.domain.models.RoleResource;

/**
 * 模型工具
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-25
 * @since 0.1
 */
public class Models {

    /** 构造函数 */
    private Models() {}

    /**
     * 新建角色资源实例
     *
     * @param type 类型
     * @param roleId 角色编号
     * @param resourceId 资源编号
     * @return 角色资源实例
     */
    public static RoleResource newRoleResource(String type, String roleId, String resourceId) {
        RoleResource roleResource = new RoleResource();
        roleResource.setType(type);
        roleResource.setRoleId(roleId);
        roleResource.setResourceId(resourceId);
        return roleResource;
    }
}

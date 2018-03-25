package net.ufrog.leo.domain.models;

import net.ufrog.aries.common.jpa.Model;

/**
 * 角色资源模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-04-17
 * @since 0.1
 */
@java.lang.SuppressWarnings("unused")
@javax.persistence.Entity
@javax.persistence.Table(name = "leo_role_resource")
public class RoleResource extends Model {

    private static final long serialVersionUID = 7605765346765092854L;

    /** 类型 */
    @javax.persistence.Column(name = "dc_type")
    private java.lang.String type;

    /** 角色编号 */
    @javax.persistence.Column(name = "fk_role_id")
    private java.lang.String roleId;

    /** 资源编号 */
    @javax.persistence.Column(name = "fk_resource_id")
    private java.lang.String resourceId;

    /**
     * 读取类型
     *
     * @return 类型
     */
    public java.lang.String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }

    /**
     * 读取角色编号
     *
     * @return 角色编号
     */
    public java.lang.String getRoleId() {
        return roleId;
    }

    /**
     * 设置角色编号
     *
     * @param roleId 角色编号
     */
    public void setRoleId(java.lang.String roleId) {
        this.roleId = roleId;
    }

    /**
     * 读取资源编号
     *
     * @return 资源编号
     */
    public java.lang.String getResourceId() {
        return resourceId;
    }

    /**
     * 设置资源编号
     *
     * @param resourceId 资源编号
     */
    public void setResourceId(java.lang.String resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * 类型
     *
     * @author ultrafrog
     * @version 0.1, 2017-04-17
     * @since 0.1
     */
    public static final class Type {

        @net.ufrog.common.dict.Element("禁止")
        public static final String BAN = "00";

        @net.ufrog.common.dict.Element("允许")
        public static final String ALLOW = "01";
    }
}
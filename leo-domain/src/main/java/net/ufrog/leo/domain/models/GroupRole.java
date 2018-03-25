package net.ufrog.leo.domain.models;

import net.ufrog.aries.common.jpa.Model;

/**
 * 组织角色模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-12-28
 * @since 0.1
 */
@java.lang.SuppressWarnings("unused")
@javax.persistence.Entity
@javax.persistence.Table(name = "leo_group_role")
public class GroupRole extends Model {

    private static final long serialVersionUID = -2077941700942048158L;

    /** 组织编号 */
    @javax.persistence.Column(name = "fk_group_id")
    private java.lang.String groupId;

    /** 角色编号 */
    @javax.persistence.Column(name = "fk_role_id")
    private java.lang.String roleId;

    /**
     * 读取组织编号
     *
     * @return 组织编号
     */
    public java.lang.String getGroupId() {
        return groupId;
    }

    /**
     * 设置组织编号
     *
     * @param groupId 组织编号
     */
    public void setGroupId(java.lang.String groupId) {
        this.groupId = groupId;
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
}
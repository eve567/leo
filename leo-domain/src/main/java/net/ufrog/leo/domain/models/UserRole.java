package net.ufrog.leo.domain.models;

/**
 * 用户角色模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-04-17
 * @since 0.1
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "leo_user_role")
public class UserRole extends Model {

    private static final long serialVersionUID = -822772543992360925L;

    /** 用户编号 */
    @javax.persistence.Column(name = "fk_user_id")
    private java.lang.String userId;

    /** 角色编号 */
    @javax.persistence.Column(name = "fk_role_id")
    private java.lang.String roleId;

    /**
     * 读取用户编号
     *
     * @return 用户编号
     */
    public java.lang.String getUserId() {
        return userId;
    }

    /**
     * 设置用户编号
     *
     * @param userId 用户编号
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
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
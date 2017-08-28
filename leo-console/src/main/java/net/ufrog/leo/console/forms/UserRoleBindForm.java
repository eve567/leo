package net.ufrog.leo.console.forms;

import java.io.Serializable;

/**
 * 用户角色绑定表单
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-28
 * @since 0.1
 */
public class UserRoleBindForm implements Serializable {

    private static final long serialVersionUID = 2529931169035645012L;

    /** 用户编号 */
    private String userId;

    /** 应用编号 */
    private String appId;

    /** 绑定角色编号数组 */
    private String[] roles;

    /**
     * 读取用户编号
     *
     * @return 用户编号
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户编号
     *
     * @param userId 用户编号
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 读取应用编号
     *
     * @return 应用编号
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置应用编号
     *
     * @param appId 应用编号
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 读取绑定角色编号数组
     *
     * @return 绑定角色编号数组
     */
    public String[] getRoles() {
        return roles;
    }

    /**
     * 设置绑定角色编号数组
     *
     * @param roles 绑定角色编号数组
     */
    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}

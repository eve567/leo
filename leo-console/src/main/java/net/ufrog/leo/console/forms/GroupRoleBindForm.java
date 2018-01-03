package net.ufrog.leo.console.forms;

import java.io.Serializable;

/**
 * 组织角色绑定表单
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2018-01-02
 * @since 0.1
 */
public class GroupRoleBindForm implements Serializable {

    private static final long serialVersionUID = 908733306508929942L;

    /** 组织编号 */
    private String groupId;

    /** 应用编号 */
    private String appId;

    /** 角色编号数组 */
    private String[] roleIds;

    /**
     * 读取组织编号
     *
     * @return 组织编号
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * 设置组织编号
     *
     * @param groupId 组织编号
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
     * 读取角色编号数组
     *
     * @return 角色编号数组
     */
    public String[] getRoleIds() {
        return roleIds;
    }

    /**
     * 设置角色编号数组
     *
     * @param roleIds 角色编号数组
     */
    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }
}

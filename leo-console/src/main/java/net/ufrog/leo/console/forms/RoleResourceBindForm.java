package net.ufrog.leo.console.forms;

import java.io.Serializable;

/**
 * 角色资源绑定表单
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-17
 * @since 0.1
 */
public class RoleResourceBindForm implements Serializable {

    private static final long serialVersionUID = 2558339664841773296L;

    /** 角色编号 */
    private String roleId;

    /** 类型 */
    private String type;

    /** 允许资源数组 */
    private String[] allows;

    /** 禁用资源数组 */
    private String[] bans;

    /**
     * 读取角色编号
     *
     * @return 角色编号
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * 设置角色编号
     *
     * @param roleId 角色编号
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 读取类型
     *
     * @return 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 读取绑定资源数组
     *
     * @return 绑定资源数组
     */
    public String[] getAllows() {
        return allows;
    }

    /**
     * 设置绑定资源数组
     *
     * @param allows 绑定资源数组
     */
    public void setAllows(String[] allows) {
        this.allows = allows;
    }

    /**
     * 读取禁用资源数组
     *
     * @return 禁用资源数组
     */
    public String[] getBans() {
        return bans;
    }

    /**
     * 设置禁用资源数组
     *
     * @param bans 禁用资源数组
     */
    public void setBans(String[] bans) {
        this.bans = bans;
    }
}

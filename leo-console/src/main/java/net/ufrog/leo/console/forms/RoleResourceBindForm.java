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

    /** 允许资源数组 */
    private String[] allows;

    /** 禁用资源数组 */
    private String[] bans;

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

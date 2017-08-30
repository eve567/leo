package net.ufrog.leo.client.api.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 开放平台用户请求对象
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-29
 * @since 0.1
 */
public class OpenPlatformUserReq implements Serializable {

    private static final long serialVersionUID = 4118854871131268720L;

    /** 应用编号 */
    private String appId;

    /** 电子邮件 */
    private String email;

    /** 手机号码 */
    private String cellphone;

    /** 名称 */
    private String name;

    /** 是否完全匹配 */
    private Boolean isMatchAll;

    /** 是否自动创建用户 */
    private Boolean isAutoCreate;

    /** 参数映射 */
    private Map<String, String> values;

    /** 构造函数 */
    public OpenPlatformUserReq() {
        this.isMatchAll = Boolean.FALSE;
        this.isAutoCreate = Boolean.FALSE;
        this.values = new HashMap<>();
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
     * 读取电子邮件
     *
     * @return 电子邮件
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮件
     *
     * @param email 电子邮件
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 读取手机号码
     *
     * @return 手机号码
     */
    public String getCellphone() {
        return cellphone;
    }

    /**
     * 设置手机号码
     *
     * @param cellphone 手机号码
     */
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    /**
     * 读取名称
     *
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 读取是否完全匹配
     *
     * @return 是否完全匹配
     */
    public Boolean getIsMatchAll() {
        return isMatchAll;
    }

    /**
     * 设置是否完全匹配
     *
     * @param isMatchAll 是否完全匹配
     */
    public void setIsMatchAll(Boolean isMatchAll) {
        this.isMatchAll = isMatchAll;
    }

    /**
     * 读取是否自动创建用户
     *
     * @return 是否自动创建用户
     */
    public Boolean getIsAutoCreate() {
        return isAutoCreate;
    }

    /**
     * 设置是否自动创建用户
     *
     * @param isAutoCreate 是否自动创建用户
     */
    public void setIsAutoCreate(Boolean isAutoCreate) {
        this.isAutoCreate = isAutoCreate;
    }

    /**
     * 读取参数映射
     *
     * @return 参数映射
     */
    public Map<String, String> getValues() {
        return values;
    }
}

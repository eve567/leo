package net.ufrog.leo.domain.models;

import net.ufrog.aries.common.jpa.Model;

/**
 * 视图模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2018-08-13
 * @since 0.1
 */
@java.lang.SuppressWarnings("unused")
@javax.persistence.Entity
@javax.persistence.Table(name = "leo_view")
public class View extends Model {

    private static final long serialVersionUID = -5692373908836744946L;

    /** 名称 */
    @javax.persistence.Column(name = "vc_name")
    private java.lang.String name;

    /** 代码 */
    @javax.persistence.Column(name = "vc_code")
    private java.lang.String code;

    /** 应用编号 */
    @javax.persistence.Column(name = "fk_app_id")
    private java.lang.String appId;

    /**
     * 读取名称
     *
     * @return 名称
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    /**
     * 读取代码
     *
     * @return 代码
     */
    public java.lang.String getCode() {
        return code;
    }

    /**
     * 设置代码
     *
     * @param code 代码
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }

    /**
     * 读取应用编号
     *
     * @return 应用编号
     */
    public java.lang.String getAppId() {
        return appId;
    }

    /**
     * 设置应用编号
     *
     * @param appId 应用编号
     */
    public void setAppId(java.lang.String appId) {
        this.appId = appId;
    }
}
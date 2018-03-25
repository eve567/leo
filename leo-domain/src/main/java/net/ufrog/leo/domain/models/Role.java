package net.ufrog.leo.domain.models;

import net.ufrog.aries.common.jpa.Model;
import net.ufrog.common.dict.Dicts;

/**
 * 角色模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-04-17
 * @since 0.1
 */
@java.lang.SuppressWarnings("unused")
@javax.persistence.Entity
@javax.persistence.Table(name = "leo_role")
public class Role extends Model {

    private static final long serialVersionUID = -2094158452244013092L;

    /** 名称 */
    @javax.persistence.Column(name = "vc_name")
    private java.lang.String name;

    /** 代码 */
    @javax.persistence.Column(name = "vc_code")
    private java.lang.String code;

    /** 类型 */
    @javax.persistence.Column(name = "dc_type")
    private java.lang.String type;

    /** 状态 */
    @javax.persistence.Column(name = "dc_status")
    private java.lang.String status;

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
     * 读取状态
     *
     * @return 状态
     */
    public java.lang.String getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
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

    /**
     * 读取状态名称
     *
     * @return 状态名称
     */
    public String getStatusName() {
        return Dicts.name(status, Status.class);
    }

    /**
     * 类型
     *
     * @author ultrafrog
     * @version 0.1, 2017-04-17
     * @since 0.1
     */
    public static final class Type {

        @net.ufrog.common.dict.Element("私有")
        public static final String PRIVATE = "00";

        @net.ufrog.common.dict.Element("公有")
        public static final String PUBLIC = "01";
    }

    /**
     * 状态
     *
     * @author ultrafrog
     * @version 0.1, 2017-04-17
     * @since 0.1
     */
    public static final class Status {

        @net.ufrog.common.dict.Element("有效")
        public static final String ENABLED = "10";

        @net.ufrog.common.dict.Element("无效")
        public static final String DISABLED = "99";
    }
}
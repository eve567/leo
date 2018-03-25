package net.ufrog.leo.domain.models;

import net.ufrog.aries.common.jpa.Model;
import net.ufrog.common.dict.Dicts;

/**
 * 应用资源模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-14
 * @since 0.1
 */
@java.lang.SuppressWarnings("unused")
@javax.persistence.Entity
@javax.persistence.Table(name = "leo_app_resource")
public class AppResource extends Model {

    private static final long serialVersionUID = 9009563796035752783L;

    /** 类型 */
    @javax.persistence.Column(name = "dc_type")
    private java.lang.String type;

    /** 应用编号 */
    @javax.persistence.Column(name = "fk_app_id")
    private java.lang.String appId;

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
     * 读取类型名称
     *
     * @return 类型名称
     */
    public String getTypeName() {
        return Dicts.name(type, Resource.Type.class);
    }
}
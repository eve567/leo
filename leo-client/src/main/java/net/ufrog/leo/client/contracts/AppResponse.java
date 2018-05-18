package net.ufrog.leo.client.contracts;

import net.ufrog.aries.common.contract.Response;

/**
 * 应用响应
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-18
 * @since 3.0.0
 */
public class AppResponse extends Response {

    private static final long serialVersionUID = 6951688888075178549L;

    /** 代码 */
    private java.lang.String code;

    /** 色值 */
    private java.lang.String color;

    /** 编号 */
    private java.lang.String id;

    /** 图标 */
    private java.lang.String logo;

    /** 名称 */
    private java.lang.String name;

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
     * 读取色值
     *
     * @return 色值
     */
    public java.lang.String getColor() {
        return color;
    }

    /**
     * 设置色值
     *
     * @param color 色值
     */
    public void setColor(java.lang.String color) {
        this.color = color;
    }

    /**
     * 读取编号
     *
     * @return 编号
     */
    public java.lang.String getId() {
        return id;
    }

    /**
     * 设置编号
     *
     * @param id 编号
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    /**
     * 读取图标
     *
     * @return 图标
     */
    public java.lang.String getLogo() {
        return logo;
    }

    /**
     * 设置图标
     *
     * @param logo 图标
     */
    public void setLogo(java.lang.String logo) {
        this.logo = logo;
    }

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
}
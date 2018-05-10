package net.ufrog.leo.client.contract;

import net.ufrog.aries.common.contract.Response;

/**
 * 导航响应
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-17
 * @since 3.0.0
 */
public class NavResponse extends Response {

    private static final long serialVersionUID = 5507652887967901531L;

    /** 代码 */
    private java.lang.String code;

    /** 编号 */
    private java.lang.String id;

    /** 名称 */
    private java.lang.String name;

    /** 副称 */
    private java.lang.String subname;

    /** 目标 */
    private java.lang.String target;

    /** 地址 */
    private java.lang.String url;

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
     * 读取副称
     *
     * @return 副称
     */
    public java.lang.String getSubname() {
        return subname;
    }

    /**
     * 设置副称
     *
     * @param subname 副称
     */
    public void setSubname(java.lang.String subname) {
        this.subname = subname;
    }

    /**
     * 读取目标
     *
     * @return 目标
     */
    public java.lang.String getTarget() {
        return target;
    }

    /**
     * 设置目标
     *
     * @param target 目标
     */
    public void setTarget(java.lang.String target) {
        this.target = target;
    }

    /**
     * 读取地址
     *
     * @return 地址
     */
    public java.lang.String getUrl() {
        return url;
    }

    /**
     * 设置地址
     *
     * @param url 地址
     */
    public void setUrl(java.lang.String url) {
        this.url = url;
    }
}
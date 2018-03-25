package net.ufrog.leo.domain.models;

import net.ufrog.aries.common.jpa.Model;
import net.ufrog.common.dict.Dicts;

/**
 * 应用模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-16
 * @since 0.1
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "leo_app")
public class App extends Model {

    private static final long serialVersionUID = -1217960652933154017L;

    /** 名称 */
    @javax.persistence.Column(name = "vc_name")
    private java.lang.String name;

    /** 代码 */
    @javax.persistence.Column(name = "vc_code")
    private java.lang.String code;

    /** 密钥 */
    @javax.persistence.Column(name = "vc_secret")
    private java.lang.String secret;

    /** 超时时间 */
    @javax.persistence.Column(name = "vc_timeout")
    private java.lang.String timeout;

    /** 访问地址 */
    @javax.persistence.Column(name = "vc_url")
    private java.lang.String url;

    /** 图标 */
    @javax.persistence.Column(name = "vc_logo")
    private java.lang.String logo;

    /** 色值 */
    @javax.persistence.Column(name = "vc_color")
    private java.lang.String color;

    /** 状态 */
    @javax.persistence.Column(name = "dc_status")
    private java.lang.String status;

    /** 可见 */
    @javax.persistence.Column(name = "dc_visible")
    private java.lang.String visible;

    /** 多用户 */
    @javax.persistence.Column(name = "dc_mulriple")
    private java.lang.String mulriple;

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
     * 读取密钥
     *
     * @return 密钥
     */
    public java.lang.String getSecret() {
        return secret;
    }

    /**
     * 设置密钥
     *
     * @param secret 密钥
     */
    public void setSecret(java.lang.String secret) {
        this.secret = secret;
    }

    /**
     * 读取超时时间
     *
     * @return 超时时间
     */
    public java.lang.String getTimeout() {
        return timeout;
    }

    /**
     * 设置超时时间
     *
     * @param timeout 超时时间
     */
    public void setTimeout(java.lang.String timeout) {
        this.timeout = timeout;
    }

    /**
     * 读取访问地址
     *
     * @return 访问地址
     */
    public java.lang.String getUrl() {
        return url;
    }

    /**
     * 设置访问地址
     *
     * @param url 访问地址
     */
    public void setUrl(java.lang.String url) {
        this.url = url;
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
     * 读取可见
     *
     * @return 可见
     */
    public java.lang.String getVisible() {
        return visible;
    }

    /**
     * 设置可见
     *
     * @param visible 可见
     */
    public void setVisible(java.lang.String visible) {
        this.visible = visible;
    }

    /**
     * 读取多用户
     *
     * @return 多用户
     */
    public java.lang.String getMulriple() {
        return mulriple;
    }

    /**
     * 设置多用户
     *
     * @param mulriple 多用户
     */
    public void setMulriple(java.lang.String mulriple) {
        this.mulriple = mulriple;
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
     * 读取可见名称
     *
     * @return 可见名称
     */
    public String getVisibleName() {
        return Dicts.name(visible, Visible.class);
    }

    /**
     * 读取多用户名称
     *
     * @return 多用户名称
     */
    public String getMulripleName() {
        return Dicts.name(mulriple, Mulriple.class);
    }

    /**
     * 状态
     *
     * @author ultrafrog
     * @version 0.1, 2017-03-16
     * @since 0.1
     */
    public static final class Status {

        @net.ufrog.common.dict.Element("离线")
        public static final String OFFLINE = "00";

        @net.ufrog.common.dict.Element("在线")
        public static final String ONLINE = "01";
    }

    /**
     * 可见
     *
     * @author ultrafrog
     * @version 0.1, 2017-03-16
     * @since 0.1
     */
    public static final class Visible {

        @net.ufrog.common.dict.Element("隐藏")
        public static final String HIDE = "00";

        @net.ufrog.common.dict.Element("显示")
        public static final String SHOW = "01";
    }

    /**
     * 多用户
     *
     * @author ultrafrog
     * @version 0.1, 2017-03-16
     * @since 0.1
     */
    public static final class Mulriple {

        @net.ufrog.common.dict.Element("同登互踢")
        public static final String FALSE = "00";

        @net.ufrog.common.dict.Element("同登允许")
        public static final String TRUE = "01";
    }
}
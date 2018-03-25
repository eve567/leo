package net.ufrog.leo.domain.models;

import net.ufrog.aries.common.jpa.Model;

/**
 * 系统属性模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-23
 * @since 0.1
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "leo_prop")
public class Prop extends Model {

    private static final long serialVersionUID = 7978150944444999920L;

    /** 代码 */
    @javax.persistence.Column(name = "vc_code")
    private java.lang.String code;

    /** 内容 */
    @javax.persistence.Column(name = "vc_value")
    private java.lang.String value;

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
     * 读取内容
     *
     * @return 内容
     */
    public java.lang.String getValue() {
        return value;
    }

    /**
     * 设置内容
     *
     * @param value 内容
     */
    public void setValue(java.lang.String value) {
        this.value = value;
    }
}
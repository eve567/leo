package net.ufrog.leo.domain.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户属性模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-17
 * @since 0.1
 */
@Entity
@Table(name = "leo_user_prop")
public class UserProp extends Model {

    private static final long serialVersionUID = -1123153644522801649L;

    /** 代码 */
    @Column(name = "vc_code")
    private String code;

    /** 内容 */
    @Column(name = "vc_value")
    private String value;

    /** 用户编号 */
    @Column(name = "fk_user_id")
    private String userId;

    /**
     * 读取代码
     *
     * @return 代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置代码
     *
     * @param code 代码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 读取内容
     *
     * @return 内容
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置内容
     *
     * @param value 内容
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 读取用户编号
     *
     * @return 用户编号
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户编号
     *
     * @param userId 用户编号
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}

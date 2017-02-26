package net.ufrog.leo.domain.models;

import net.ufrog.common.dict.Dicts;
import net.ufrog.common.dict.Element;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-02-18
 * @since 0.1
 */
@Entity
@Table(name = "leo_user")
public class User extends Model {

    private static final long serialVersionUID = -2264989599259175132L;

    /** 帐号 */
    @Column(name = "vc_account")
    private String account;

    /** 电子邮件 */
    @Column(name = "vc_email")
    private String email;

    /** 手机号码 */
    @Column(name = "vc_cellphone")
    private String cellphone;

    /** 姓名 */
    @Column(name = "vc_name")
    private String name;

    /** 密码 */
    @Column(name = "vc_password")
    private String password;

    /** 状态 */
    @Column(name = "dc_status")
    private String status;

    /** 类型 */
    @Column(name = "dc_type")
    private String type;

    /**
     * 读取帐号
     *
     * @return 帐号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置帐号
     *
     * @param account 帐号
     */
    public void setAccount(String account) {
        this.account = account;
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
     * 读取姓名
     *
     * @return 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 读取密码
     *
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 读取状态
     *
     * @return 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 读取类型
     *
     * @return 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
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
     * 读取类型名称
     *
     * @return 类型名称
     */
    public String getTypeName() {
        return Dicts.name(type, Type.class);
    }

    /**
     * 状态
     *
     * @author ultrafrog
     * @version 0.1, 2016-08-16
     * @since 0.1
     */
    public static final class Status {

        @Element("待确认")
        public static final String PENDING = "00";

        @Element("正常")
        public static final String ENABLED = "10";

        @Element("冻结")
        public static final String FROZEN = "90";

        @Element("注销")
        public static final String DISABLED = "99";
    }

    /**
     * 类型
     *
     * @author ultrafrog
     * @version 0.1, 2016-08-16
     * @since 0.1
     */
    public static final class Type {

        @Element("客户")
        public static final String CLIENT = "00";

        @Element("员工")
        public static final String STAFF = "90";

        @Element("根")
        public static final String ROOT = "99";
    }
}

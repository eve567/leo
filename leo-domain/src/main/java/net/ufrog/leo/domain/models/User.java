package net.ufrog.leo.domain.models;

/**
 * 用户模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-22
 * @since 0.1
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "leo_user")
public class User extends Model {

    private static final long serialVersionUID = -4022179879403274597L;

    /** 账号 */
    @javax.persistence.Column(name = "vc_account")
    private java.lang.String account;

    /** 电子邮件 */
    @javax.persistence.Column(name = "vc_email")
    private java.lang.String email;

    /** 手机号码 */
    @javax.persistence.Column(name = "vc_cellphone")
    private java.lang.String cellphone;

    /** 称呼 */
    @javax.persistence.Column(name = "vc_name")
    private java.lang.String name;

    /** 密码 */
    @javax.persistence.Column(name = "vc_password")
    private java.lang.String password;

    /** 状态 */
    @javax.persistence.Column(name = "dc_status")
    private java.lang.String status;

    /** 类型 */
    @javax.persistence.Column(name = "dc_type")
    private java.lang.String type;

    /** 强制修改密码 */
    @javax.persistence.Column(name = "dc_forced")
    private java.lang.String forced;

    /**
     * 读取账号
     *
     * @return 账号
     */
    public java.lang.String getAccount() {
        return account;
    }

    /**
     * 设置账号
     *
     * @param account 账号
     */
    public void setAccount(java.lang.String account) {
        this.account = account;
    }

    /**
     * 读取电子邮件
     *
     * @return 电子邮件
     */
    public java.lang.String getEmail() {
        return email;
    }

    /**
     * 设置电子邮件
     *
     * @param email 电子邮件
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    /**
     * 读取手机号码
     *
     * @return 手机号码
     */
    public java.lang.String getCellphone() {
        return cellphone;
    }

    /**
     * 设置手机号码
     *
     * @param cellphone 手机号码
     */
    public void setCellphone(java.lang.String cellphone) {
        this.cellphone = cellphone;
    }

    /**
     * 读取称呼
     *
     * @return 称呼
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * 设置称呼
     *
     * @param name 称呼
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    /**
     * 读取密码
     *
     * @return 密码
     */
    public java.lang.String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
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
     * 读取强制修改密码
     *
     * @return 强制修改密码
     */
    public java.lang.String getForced() {
        return forced;
    }

    /**
     * 设置强制修改密码
     *
     * @param forced 强制修改密码
     */
    public void setForced(java.lang.String forced) {
        this.forced = forced;
    }

    /**
     * 状态
     *
     * @author ultrafrog
     * @version 0.1, 2017-03-22
     * @since 0.1
     */
    public static final class Status {

        @net.ufrog.common.dict.Element("待确认")
        public static final String PENDING = "00";

        @net.ufrog.common.dict.Element("正常")
        public static final String ENABLED = "10";

        @net.ufrog.common.dict.Element("冻结")
        public static final String FROZEN = "90";

        @net.ufrog.common.dict.Element("注销")
        public static final String DISABLED = "99";
    }

    /**
     * 类型
     *
     * @author ultrafrog
     * @version 0.1, 2017-03-22
     * @since 0.1
     */
    public static final class Type {

        @net.ufrog.common.dict.Element("客户")
        public static final String CLIENT = "00";

        @net.ufrog.common.dict.Element("员工")
        public static final String STAFF = "90";

        @net.ufrog.common.dict.Element("上帝")
        public static final String ROOT = "99";
    }

    /**
     * 强制修改密码
     *
     * @author ultrafrog
     * @version 0.1, 2017-03-22
     * @since 0.1
     */
    public static final class Forced {

        @net.ufrog.common.dict.Element("否")
        public static final String FALSE = "00";

        @net.ufrog.common.dict.Element("是")
        public static final String TRUE = "01";
    }
}
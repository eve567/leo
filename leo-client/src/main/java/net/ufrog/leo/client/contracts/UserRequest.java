package net.ufrog.leo.client.contracts;

import net.ufrog.aries.common.contract.Request;

/**
 * 用户请求
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-08
 * @since 3.0.0
 */
public class UserRequest extends Request {

    private static final long serialVersionUID = -3064488777657685223L;

    /** 账号 */
    private java.lang.String account;

    /** 手机号码 */
    private java.lang.String cellphone;

    /** 电子邮件 */
    private java.lang.String email;

    /** 名称 */
    private java.lang.String name;

    /** 密码 */
    private java.lang.String password;

    /** 类型 */
    private java.lang.String type;

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
     * 类型
     *
     * @author ultrafrog
     * @version 3.0.0, 2018-04-11
     * @since 3.0.0
     */
    public static final class Type {

        @net.ufrog.common.dict.Element("客户")
        public static final String CLIENT = "00";

        @net.ufrog.common.dict.Element("员工")
        public static final String STAFF = "90";
    }
}
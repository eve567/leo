package net.ufrog.leo.client.contracts;

import net.ufrog.aries.common.contract.Request;
import net.ufrog.common.dict.Element;

/**
 * 鉴权认证请求
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 4.0.1, 2018-05-16
 * @since 4.0.1
 */
public class AuthenticateRequest extends Request {

    private static final long serialVersionUID = 5714403425410322286L;

    /** 账号 */
    private java.lang.String account;

    /** 应用编号 */
    private java.lang.String appId;

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
     * @author ultrafrog, ufrog.net@gmail.com
     * @version 3.0.0, 2018-05-16
     * @since 3.0.0
     */
    public static final class Type {

        @Element("客户端")
        public static final String CLIENT   = "client";

        @Element("控制端")
        public static final String CONSOLE  = "console";
    }
}
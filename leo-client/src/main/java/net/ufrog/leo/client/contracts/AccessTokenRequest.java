package net.ufrog.leo.client.contracts;

import net.ufrog.aries.common.contract.Request;

/**
 * 访问令牌请求
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 4.0.1, 2018-05-16
 * @since 4.0.1
 */
public class AccessTokenRequest extends Request {

    private static final long serialVersionUID = 5596235851062723811L;

    /** 应用编号 */
    private java.lang.String appId;

    /** 远程地址 */
    private java.lang.String remoteAddress;

    /** 用户账号 */
    private java.lang.String userAccount;

    /** 用户编号 */
    private java.lang.String userId;

    /** 用户名称 */
    private java.lang.String userName;

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
     * 读取远程地址
     *
     * @return 远程地址
     */
    public java.lang.String getRemoteAddress() {
        return remoteAddress;
    }

    /**
     * 设置远程地址
     *
     * @param remoteAddress 远程地址
     */
    public void setRemoteAddress(java.lang.String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    /**
     * 读取用户账号
     *
     * @return 用户账号
     */
    public java.lang.String getUserAccount() {
        return userAccount;
    }

    /**
     * 设置用户账号
     *
     * @param userAccount 用户账号
     */
    public void setUserAccount(java.lang.String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * 读取用户编号
     *
     * @return 用户编号
     */
    public java.lang.String getUserId() {
        return userId;
    }

    /**
     * 设置用户编号
     *
     * @param userId 用户编号
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    /**
     * 读取用户名称
     *
     * @return 用户名称
     */
    public java.lang.String getUserName() {
        return userName;
    }

    /**
     * 设置用户名称
     *
     * @param userName 用户名称
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }
}
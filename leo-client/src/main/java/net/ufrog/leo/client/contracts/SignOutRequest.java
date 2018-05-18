package net.ufrog.leo.client.contracts;

import net.ufrog.aries.common.contract.Request;

/**
 * 注销请求
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 4.0.1, 2018-05-16
 * @since 4.0.1
 */
public class SignOutRequest extends Request {

    private static final long serialVersionUID = 235992936507034303L;

    /** 应用编号 */
    private java.lang.String appId;

    /** 令牌 */
    private java.lang.String token;

    /** 用户编号 */
    private java.lang.String userId;

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
     * 读取令牌
     *
     * @return 令牌
     */
    public java.lang.String getToken() {
        return token;
    }

    /**
     * 设置令牌
     *
     * @param token 令牌
     */
    public void setToken(java.lang.String token) {
        this.token = token;
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
}
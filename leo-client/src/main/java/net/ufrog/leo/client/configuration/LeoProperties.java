package net.ufrog.leo.client.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-17
 * @since 3.0.0
 */
@ConfigurationProperties(prefix = "ufrog.app.config.leo")
public class LeoProperties {

    /** 地址 */
    private String host;

    /** 应用编号 */
    private String appId;

    /** 应用密钥 */
    private String appSecret;

    /** 访问令牌管理器 */
    private String accessTokenManager;

    /**
     * 读取地址
     *
     * @return 地址
     */
    public String getHost() {
        return host;
    }

    /**
     * 设置地址
     *
     * @param host 地址
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 读取应用编号
     *
     * @return 应用编号
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置应用编号
     *
     * @param appId 应用编号
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 读取应用密钥
     *
     * @return 应用密钥
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     * 设置应用密钥
     *
     * @param appSecret 应用密钥
     */
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    /**
     * 读取访问令牌管理器
     *
     * @return 访问令牌管理器
     */
    public String getAccessTokenManager() {
        return accessTokenManager;
    }

    /**
     * 设置访问令牌管理器
     *
     * @param accessTokenManager 访问令牌管理器
     */
    public void setAccessTokenManager(String accessTokenManager) {
        this.accessTokenManager = accessTokenManager;
    }
}

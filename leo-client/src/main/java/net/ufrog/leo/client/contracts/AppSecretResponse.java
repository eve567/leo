package net.ufrog.leo.client.contracts;

import net.ufrog.aries.common.contract.Response;

/**
 * 应用密钥响应
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 4.0.1, 2018-07-04
 * @since 4.0.1
 */
public class AppSecretResponse extends Response {

    private static final long serialVersionUID = 8624728371694443446L;

    /** 编号 */
    private String id;

    /** 密钥 */
    private String secret;

    /** 构造函数 */
    public AppSecretResponse() {}

    /**
     * 构造函数
     *
     * @param id 编号
     * @param secret 密钥
     */
    public AppSecretResponse(String id, String secret) {
        this();
        this.id = id;
        this.secret = secret;
    }

    /**
     * 读取编号
     *
     * @return 编号
     */
    public String getId() {
        return id;
    }

    /**
     * 设置编号
     *
     * @param id 编号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 读取密钥
     *
     * @return 密钥
     */
    public String getSecret() {
        return secret;
    }

    /**
     * 设置密钥
     *
     * @param secret 密钥
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }
}

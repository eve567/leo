package net.ufrog.leo.server.beans.access.token;

/**
 * 访问令牌管理接口
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-14
 * @since 0.1
 */
public interface AccessTokenManager {

    /**
     * 上线
     *
     * @param accessToken 访问令牌
     */
    void online(AccessToken accessToken);

    /**
     * 上线
     *
     * @param id 用户编号
     * @param account 账号
     * @param name 名称
     * @return 访问令牌
     */
    AccessToken online(String id, String account, String name);

    /**
     * 离线
     *
     * @param id 用户编号
     * @param token 令牌
     */
    void offline(String id, String token);
}

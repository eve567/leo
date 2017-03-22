package net.ufrog.leo.server.auth;

import net.ufrog.common.app.AppUser;
import net.ufrog.leo.domain.models.App;

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
     * @param appUser 应用用户
     * @param app 应用
     * @param ip 地址
     * @return 访问令牌
     */
    AccessToken online(AppUser appUser, App app, String ip);

    /**
     * 离线
     *
     * @param userId 用户编号
     * @param appId 应用编号
     * @param token 令牌
     */
    void offline(String userId, String appId, String token);
}

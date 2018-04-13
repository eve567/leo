package net.ufrog.leo.server.accesstoken;

import net.ufrog.aries.common.exception.AriesException;
import net.ufrog.common.app.AppUser;
import net.ufrog.common.spring.app.SpringWebApp;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.client.contract.ResultCode;
import net.ufrog.leo.domain.models.App;

import java.util.List;

/**
 * 访问令牌管理抽象
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-11
 * @since 3.0.0
 */
public abstract class AccessTokenManager {

    /**
     * 上线
     *
     * @param accessToken 访问令牌
     */
    public abstract void online(AccessToken accessToken);

    /**
     * 离线
     *
     * @param userId 用户编号
     * @param appId 应用编号
     * @param token 令牌
     */
    public abstract void offline(String userId, String appId, String token);

    /**
     * 读取访问令牌
     *
     * @param token 令牌
     * @param appId 应用编号
     * @return 访问令牌
     */
    public abstract AccessToken get(String token, String appId);

    /**
     * 读取所有访问令牌
     *
     * @return 访问令牌列表
     */
    public abstract List<AccessToken> getAll();

    /**
     * 上线
     *
     * @param appUser 应用用户
     * @param app 应用
     * @param remote 远程地址
     * @return 访问令牌
     */
    public AccessToken online(AppUser appUser, App app,String remote) {
        AccessToken accessToken = new AccessToken(appUser, app, remote);
        online(accessToken);
        return accessToken;
    }

    /**
     * 检查访问令牌是否合法
     *
     * @param accessToken 访问令牌
     * @param appId 应用编号
     */
    public void validate(AccessToken accessToken, String appId) {
        if (accessToken == null) {
            throw new AriesException(ResultCode.NOT_SIGN);
        } else if (accessToken.isExpiry()) {
            offline(accessToken.getUserId(), accessToken.getAppId(), accessToken.getToken());
            throw new AriesException(ResultCode.NOT_SIGN);
        } else if (!Strings.equals(accessToken.getAppId(), appId)) {
            throw new AriesException(ResultCode.NOT_SIGN);
        }
    }

    /**
     * 读取令牌管理器
     *
     * @return 令牌管理器
     */
    public static AccessTokenManager get() {
        return SpringWebApp.getBean(AccessTokenManager.class);
    }
}

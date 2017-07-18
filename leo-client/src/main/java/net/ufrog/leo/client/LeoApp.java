package net.ufrog.leo.client;

import net.ufrog.common.Logger;
import net.ufrog.common.app.App;
import net.ufrog.common.app.AppUser;
import net.ufrog.common.cache.Caches;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.spring.app.SpringWebApp;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.client.api.APIs;
import net.ufrog.leo.client.api.beans.AppUserResp;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-06
 * @since 0.1
 */
public class LeoApp extends SpringWebApp {

    private static final String SESSION_TOKEN   = "leo_session_token";
    private static final String CACHE_TOKEN     = "leo_cache_token";

    /**
     * 构造函数
     *
     * @param request 请求
     * @param response 响应
     */
    private LeoApp(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public AppUser getUser() {
        String token = session(SESSION_TOKEN, String.class);
        if (Strings.empty(token)) {
            Logger.warn("cannot find user token.");
            return null;
        } else {
            LeoAppUser leoAppUser = Caches.get(CACHE_TOKEN, token, LeoAppUser.class);
            if (leoAppUser == null) {
                // 请求当前用户
                AppUserResp appUserResp = APIs.getUser(token, LeoConfig.getLeoAppId());
                if (appUserResp.isSuccess()) {
                    leoAppUser = new LeoAppUser(appUserResp.getId(), appUserResp.getAccount(), appUserResp.getName());
                    leoAppUser.setToken(appUserResp.getToken());
                    Caches.set(CACHE_TOKEN, token, leoAppUser, "5min");
                    Logger.debug("cache app user by token: %s", token);
                } else {
                    Logger.warn("cannot find user by token: %s, appId: %s", token, LeoConfig.getLeoAppId());
                }
            } else {
                Logger.trace("it's from cache.");
            }
            return leoAppUser;
        }
    }

    @Override
    public AppUser setUser(AppUser user) {
        throw new UnsupportedOperationException();
    }

    /**
     * 设置访问令牌
     *
     * @param accessToken 访问令牌
     */
    public void setAccessToken(String accessToken) {
        session(SESSION_TOKEN, accessToken);
    }

    /**
     * @return leo host
     */
    public String getHost() {
        return LeoConfig.getLeoHost();
    }

    /**
     * @return leo app id
     */
    public String getAppId() {
        return LeoConfig.getLeoAppId();
    }

    /**
     * @return leo app security
     */
    public String getAppSecret() {
        return LeoConfig.getLeoAppSecret();
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    public static void initialize(ServletContext context) {
        SpringWebApp.initialize(context);
    }

    /**
     * 创建实例
     *
     * @param request 请求
     * @param response 响应
     * @return 当前线程应用
     */
    public static App create(HttpServletRequest request, HttpServletResponse response) {
        current(new LeoApp(request, response));
        return current();
    }
}

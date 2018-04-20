package net.ufrog.leo.client.app;

import net.ufrog.common.Logger;
import net.ufrog.common.app.App;
import net.ufrog.common.app.AppUser;
import net.ufrog.common.cache.Caches;
import net.ufrog.common.spring.app.SpringWebApp;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.client.LeoClient;
import net.ufrog.leo.client.contract.AppUserResp;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户中心应用
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-11
 * @since 3.0.0
 */
public class LeoApp extends SpringWebApp {

    private static final String SESSION_TOKEN   = "leo_session_token";
    private static final String CACHE_TOKEN     = "leo_cache_token";

    /**
     * 构造函数
     *
     * @param request  请求
     * @param response 响应
     */
    public LeoApp(HttpServletRequest request, HttpServletResponse response) {
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
            LeoClient leoClient = SpringWebApp.getBean(LeoClient.class);

            if (leoAppUser == null) {
                Logger.debug("find user by appId: {}, token: {}", getAppId(), token);
                AppUserResp appUserResp = leoClient.getUser(getAppId(), token);
                if (appUserResp.isSuccess()) {
                    leoAppUser = new LeoAppUser(LeoUserIdConverter.convert(appUserResp.getId()), appUserResp.getAccount(), appUserResp.getName());
                    leoAppUser.setToken(appUserResp.getToken());
                    Caches.set(CACHE_TOKEN, token, leoAppUser, "5min");
                    Logger.debug("cache app user by token: %s", token);
                } else {
                    Logger.warn("cannot find user by token: %s, appId: %s", token, getAppId());
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
     * 读取访问令牌
     *
     * @return 访问令牌
     */
    public String getAccessToken() {
        return LeoAppUser.class.cast(getUser()).getToken();
    }

    /**
     * 读取应用编号
     *
     * @return 应用编号
     */
    public String getAppId() {
        return App.config("leo.appId");
    }

    /**
     * 读取应用密钥
     *
     * @return 应用密钥
     */
    public String getAppSecret() {
        return App.config("leo.appSecret");
    }

    /**
     * 设置访问令牌
     *
     * @param accessToken 令牌
     */
    void setAccessToken(String accessToken) {
        session(SESSION_TOKEN, accessToken);
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
    public static LeoApp create(HttpServletRequest request, HttpServletResponse response) {
        LeoApp leoApp = new LeoApp(request, response);
        current(leoApp);
        return leoApp;
    }
}

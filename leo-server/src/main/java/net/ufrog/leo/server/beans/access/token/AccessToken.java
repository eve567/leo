package net.ufrog.leo.server.beans.access.token;

import net.ufrog.common.Logger;
import net.ufrog.common.app.AppUser;
import net.ufrog.common.utils.Calendars;
import net.ufrog.common.utils.Codecs;
import net.ufrog.leo.service.beans.Props;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 访问令牌
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-02-26
 * @since 0.1
 */
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 1441913437080034965L;

    /** 令牌 */
    private String token;

    /** 时间戳 */
    private Long timestamp;

    /** 应用用户 */
    private AppUser appUser;

    /** 应用映射 */
    private Map<String, Long> mApp;

    /** 构造函数 */
    private AccessToken() {
        token = Codecs.uuid();
        timestamp = System.currentTimeMillis();
        mApp = new HashMap<>();
    }

    /**
     * 构造函数
     *
     * @param appUser 应用用户
     */
    private AccessToken(AppUser appUser) {
        this();
        this.appUser = appUser;
        this.appUser.setData(token);
    }

    /**
     * 验证令牌是否有效
     *
     * @param appId 应用编号
     * @return 判断结果
     */
    public Boolean validate(String appId) {
        Long current = System.currentTimeMillis();
        Long timeout = getTimeout() * 1000L;
        Long interval = current - timestamp;

        if (interval > timeout) {
            Logger.warn("token '" + token + "' expired.");
            return Boolean.FALSE;
        } else if (!mApp.containsKey(appId)) {
            Logger.warn("token '" + token + "' has no app info by id: " + appId + ".");
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    /**
     * 读取令牌
     *
     * @return 令牌
     */
    public String getToken() {
        return token;
    }

    /**
     * 读取时间戳
     *
     * @return 时间戳
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * 读取应用用户
     *
     * @return 应用用户
     */
    public AppUser getAppUser() {
        return appUser;
    }

    /** 缓存令牌 */
    public void cache() {}

    /** 移除令牌 */
    public void remove() {}

    /**
     * 获取超时时间
     *
     * @return 超时时间
     */
    private Integer getTimeout() {
        return Calendars.parseDuration(Props.getSysSignTimeout());
    }

    /**
     * 创建访问令牌实例
     *
     * @param appUser 应用用户
     * @return 访问令牌
     */
    public static AccessToken newAccessToken(AppUser appUser) {
        AccessToken accessToken = new AccessToken(appUser);

        return null;
    }
}

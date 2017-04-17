package net.ufrog.leo.server;

import net.ufrog.common.app.AppUser;
import net.ufrog.common.utils.Calendars;
import net.ufrog.common.utils.Codecs;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.client.LeoAppUser;
import net.ufrog.leo.domain.models.App;

import java.io.Serializable;

/**
 * 访问令牌抽象
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

    /** 超时时间 */
    private Long timeout;

    /** 全程地址 */
    private String remote;

    /** 应用用户 */
    private LeoAppUser leoAppUser;

    /** 应用 */
    private App app;

    /** 构造函数 */
    private AccessToken() {
        token = Codecs.uuid();
        timestamp = System.currentTimeMillis();
    }

    /**
     * 构造函数
     *
     * @param appUser 应用用户
     * @param app 应用
     * @param remote 远程地址
     */
    public AccessToken(AppUser appUser, App app, String remote) {
        this();
        this.app = app;
        this.remote = remote;
        this.leoAppUser = (appUser instanceof LeoAppUser) ? (LeoAppUser) appUser : new LeoAppUser(appUser.getId(), appUser.getAccount(), appUser.getName());
        this.leoAppUser.setToken(token);
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
     * 读取地址
     *
     * @return 地址
     */
    public String getRemote() {
        return remote;
    }

    /**
     * 读取应用用户
     *
     * @return 应用用户
     */
    public LeoAppUser getAppUser() {
        return leoAppUser;
    }

    /**
     * 读取应用
     *
     * @return 应用
     */
    public App getApp() {
        return app;
    }

    /**
     * 读取用户编号
     *
     * @return 用户编号
     */
    public String getUserId() {
        return leoAppUser.getId();
    }

    /**
     * 读取应用编号
     *
     * @return 应用编号
     */
    public String getAppId() {
        return app.getId();
    }

    /**
     * 读取超时时间
     *
     * @return 超时时间
     */
    public Long getTimeout() {
        if (timeout == null) {
            timeout = Strings.empty(app.getTimeout()) ? -1L : Calendars.parseDuration(app.getTimeout()) * 1000L;
        }
        return timeout;
    }

    /**
     * 判断超时
     *
     * @return 判断结果
     */
    public Boolean isTimeout() {
        if (getTimeout().compareTo(-1L) == 0) return Boolean.FALSE;
        return (getTimestamp() + getTimeout()) < System.currentTimeMillis();
    }
}

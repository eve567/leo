package net.ufrog.leo.server.accesstoken;

import net.ufrog.common.app.AppUser;
import net.ufrog.common.utils.Calendars;
import net.ufrog.common.utils.Codecs;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.client.app.LeoAppUser;
import net.ufrog.leo.domain.models.App;

import java.io.Serializable;

/**
 * 访问令牌抽象
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-11
 * @since 3.0.0
 */
public class AccessToken implements Serializable {

    private static final long serialVersionUID = -1323086840837916977L;

    /** 令牌 */
    private String token;

    /** 时间戳 */
    private Long timestamp;

    /** 有效时间<br>单位 - 秒 */
    private Long expires;

    /** 远程地址 */
    private String remote;

    /** 应用用户 */
    private LeoAppUser leoAppUser;

    /** 应用 */
    private App app;

    /** 构造函数 */
    private AccessToken() {
        this.token = Codecs.uuid();
        this.timestamp = System.currentTimeMillis();
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
        this.leoAppUser = getLeoAppUser(appUser);
        this.leoAppUser.setToken(this.token);
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
     * 读取失效时间
     *
     * @return 失效时间
     */
    public Long getExpires() {
        if (expires == null) {
            expires = Strings.empty(app.getExpires()) ? - 1L : Calendars.parseDuration(app.getExpires()) * 1000L;
        }
        return expires;
    }

    /**
     * 读取远程地址
     *
     * @return 远程地址
     */
    public String getRemote() {
        return remote;
    }

    /**
     * 读取应用用户
     *
     * @return 应用用户
     */
    public LeoAppUser getLeoAppUser() {
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
     * 是否有效期满
     *
     * @return 判断结果
     */
    public Boolean isExpiry() {
        if (getExpires().compareTo(-1L) == 0) {
            return Boolean.FALSE;
        }
        return ((getTimestamp() + getExpires()) < System.currentTimeMillis());
    }

    /**
     * 获取用户中心用户
     *
     * @param appUser 应用用户
     * @return 用户中心应用用户
     */
    private LeoAppUser getLeoAppUser(AppUser appUser) {
        if (LeoAppUser.class.isInstance(appUser)) {
            return LeoAppUser.class.cast(appUser);
        } else {
            return new LeoAppUser(appUser.getId(), appUser.getAccount(), appUser.getName());
        }
    }
}

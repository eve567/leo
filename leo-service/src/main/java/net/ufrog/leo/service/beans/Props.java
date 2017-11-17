package net.ufrog.leo.service.beans;

import net.ufrog.common.cache.CacheKey;
import net.ufrog.common.cache.Caches;
import net.ufrog.common.cache.SimpleCacheKey;

/**
 * 属性工具
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-11-17
 * @since 0.1
 */
public class Props {

    public static final String THREAD_POOL_KEY_SCHEDULED    = "scheduled";

    public static final CacheKey CACHE_DEFAULT  = new SimpleCacheKey("default");
    public static final CacheKey CACHE_REDIS    = new SimpleCacheKey("redis");

    /** 字符集 */ public static final String APP_CHARSET = "app.charset";
    /** 用户开通通知邮件 */ public static final String MAIL_TPL_USER_ADD = "mail_tpl_user_add";
    /** 密码重置通知邮件 */ public static final String MAIL_TPL_USER_RESET_PASSWORD = "mail_tpl_user_reset_password";

    //
    static {
        Caches.getImpl(CACHE_DEFAULT);
    }

    /** 构造函数 */
    private Props() {}

    /**
     * 读取应用字符集
     *
     * @return 应用字符集
     */
    public static java.nio.charset.Charset getAppCharset() {
        String value = net.ufrog.common.app.App.config(APP_CHARSET, "utf-8");
        return java.nio.charset.Charset.forName(value);
    }

    /**
     * 读取用户开通通知邮件
     *
     * @return 用户开通通知邮件
     */
    public static java.lang.String getMailTplUserAdd() {
        String value = net.ufrog.common.app.App.config(MAIL_TPL_USER_ADD);
        return value;
    }

    /**
     * 读取密码重置通知邮件
     *
     * @return 密码重置通知邮件
     */
    public static java.lang.String getMailTplUserResetPassword() {
        String value = net.ufrog.common.app.App.config(MAIL_TPL_USER_RESET_PASSWORD);
        return value;
    }
}
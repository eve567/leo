package net.ufrog.leo.client;

import net.ufrog.common.app.App;

/**
 * 配置汇总
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-06
 * @since 0.1
 */
public class LeoConfig {

    private static final String KEY_LEO_URL          = "leo.url";
    private static final String KEY_LEO_APP_ID       = "leo.app.id";
    private static final String KEY_LEO_APP_SECURITY = "leo.app.security";

    /**
     * @return application config for leo url
     */
    public static String getLeoUrl() {
        return App.config(KEY_LEO_URL);
    }

    /**
     * @return application config for leo app id
     */
    public static String getLeoAppId() {
        return App.config(KEY_LEO_APP_ID);
    }

    /**
     * @return application config for leo app security
     */
    public static String getLeoAppSecurity() {
        return App.config(KEY_LEO_APP_SECURITY);
    }
}

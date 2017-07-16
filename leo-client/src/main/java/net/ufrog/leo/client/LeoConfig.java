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

    private static final String KEY_LEO_HOST        = "leo.host";
    private static final String KEY_LEO_APP_ID      = "leo.app.id";
    private static final String KEY_LEO_APP_SECRET  = "leo.app.secret";

    /**
     * @return application config for leo host
     */
    public static String getLeoHost() {
        return App.config(KEY_LEO_HOST);
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
    public static String getLeoAppSecret() {
        return App.config(KEY_LEO_APP_SECRET);
    }
}

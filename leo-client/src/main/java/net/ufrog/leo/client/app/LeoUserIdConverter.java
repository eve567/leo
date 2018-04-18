package net.ufrog.leo.client.app;

import net.ufrog.common.spring.app.SpringWebApp;

/**
 * 用户中心用户编号转换器抽象
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-11
 * @since 3.0.0
 */
public abstract class LeoUserIdConverter {

    private static LeoUserIdConverter leoUserIdConverter;

    /**
     * 转换成应用用户编号
     *
     * @param leoAppUserId 用户中心应用用户编号
     * @return 应用用户编号
     */
    public abstract String toAppUserId(String leoAppUserId);

    /**
     * 转换编号
     *
     * @param leoAppUserId 用户中心应用用户编号
     * @return 应用用户编号
     */
    static String convert(String leoAppUserId) {
        if (leoUserIdConverter == null) {
            leoUserIdConverter = SpringWebApp.getBean(LeoUserIdConverter.class);
        }
        return leoUserIdConverter.toAppUserId(leoAppUserId);
    }
}

package net.ufrog.leo.client;

import net.ufrog.common.app.App;
import net.ufrog.common.exception.ServiceException;

/**
 * 用户中心用户编号转换器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-01
 * @since 0.1
 */
public abstract class IDConverter {

    private static final String LEO_ID_CONVERTER = "leo.idConverter";

    private static IDConverter leoAppUserIdConverter;

    /**
     * 转换成应用用户编号
     *
     * @param leoAppUserId 用户中心应用用户编号
     * @return 应用用户编号
     */
    abstract String toAppUserId(String leoAppUserId);

    /**
     * 转换编号
     *
     * @param leoAppUserId 用户中心应用用户编号
     * @return 应用用户编号
     */
    static String convert(String leoAppUserId) {
        if (leoAppUserIdConverter == null) {
            try {
                leoAppUserIdConverter = (IDConverter) Class.forName(App.config(LEO_ID_CONVERTER, DefaultIDConverter.class.getName())).newInstance();
            } catch (Throwable e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }
        return leoAppUserIdConverter.toAppUserId(leoAppUserId);
    }
}

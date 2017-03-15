package net.ufrog.leo.service.beans;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-13
 * @since 0.1
 */
public class Props {

    /**
     * @return string
     */
    public static String getLeoSignTimeout() {
        return "30min";
    }

    /**
     * @return boolean
     */
    public static Boolean getLeoSignKickout() {
        return Boolean.FALSE;
    }
}

package net.ufrog.leo.client;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-01
 * @since 0.1
 */
public class DefaultIDConverter extends IDConverter {

    @Override
    public String toAppUserId(String leoAppUserId) {
        return leoAppUserId;
    }
}

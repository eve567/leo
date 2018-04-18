package net.ufrog.leo.console;

import org.springframework.stereotype.Component;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-17
 * @since 3.0.0
 */
@Component
public class LeoUserIdConverter extends net.ufrog.leo.client.app.LeoUserIdConverter {

    @Override
    public String toAppUserId(String leoAppUserId) {
        return leoAppUserId;
    }
}

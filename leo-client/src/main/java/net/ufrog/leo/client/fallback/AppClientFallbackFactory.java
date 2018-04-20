package net.ufrog.leo.client.fallback;

import net.ufrog.aries.common.contract.ClientFallbackFactory;
import net.ufrog.aries.common.contract.PageResp;
import net.ufrog.leo.client.AppClient;
import net.ufrog.leo.client.contract.AppResp;
import org.springframework.stereotype.Component;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-13
 * @since 3.0.0
 */
@Component
public class AppClientFallbackFactory extends ClientFallbackFactory<AppClient> {

    @Override
    public AppClient getClientFallback() {
        return new AppClientFallback();
    }

    /**
     * @author ultrafrog, ufrog.net@gmail.com
     * @version 3.0.0, 2018-04-13
     * @since 3.0.0
     */
    static class AppClientFallback implements AppClient {

        @Override
        public AppResp read(String id) {
            return null;
        }

        @Override
        public PageResp<AppResp> read() {
            return null;
        }
    }
}

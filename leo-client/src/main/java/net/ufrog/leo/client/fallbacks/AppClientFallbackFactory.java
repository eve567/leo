package net.ufrog.leo.client.fallbacks;

import feign.hystrix.FallbackFactory;
import net.ufrog.aries.common.contract.PageResp;
import net.ufrog.common.Logger;
import net.ufrog.leo.client.AppClient;
import net.ufrog.leo.client.contract.AppResp;
import org.springframework.stereotype.Component;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-13
 * @since 3.0.0
 */
@Component
public class AppClientFallbackFactory implements FallbackFactory<AppClient> {

    @Override
    public AppClient create(Throwable throwable) {
        Logger.warn("fallback, reason was: {}", throwable.getMessage());
        return new AppClientFallback();
    }

    /**
     * @author ultrafrog, ufrog.net@gmail.com
     * @version 3.0.0, 2018-04-13
     * @since 3.0.0
     */
    class AppClientFallback implements AppClient {

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

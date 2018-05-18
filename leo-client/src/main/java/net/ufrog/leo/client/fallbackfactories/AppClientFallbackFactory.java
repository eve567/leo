package net.ufrog.leo.client.fallbackfactories;

import net.ufrog.aries.common.contract.ClientFallbackFactory;
import net.ufrog.aries.common.contract.PageResponse;
import net.ufrog.leo.client.AppClient;
import net.ufrog.leo.client.contracts.AppResponse;
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
        return new AppClient() {

            @Override
            public AppResponse read(String id) {
                return null;
            }

            @Override
            public PageResponse<AppResponse> read() {
                return null;
            }
        };
    }
}

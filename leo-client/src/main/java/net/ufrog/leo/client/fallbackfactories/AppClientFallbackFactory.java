package net.ufrog.leo.client.fallbackfactories;

import net.ufrog.aries.common.contract.ClientFallbackFactory;
import net.ufrog.aries.common.contract.ListResponse;
import net.ufrog.aries.common.contract.PageResponse;
import net.ufrog.aries.common.contract.Response;
import net.ufrog.common.dict.Dicts;
import net.ufrog.leo.client.AppClient;
import net.ufrog.leo.client.contracts.AppResponse;
import net.ufrog.leo.client.contracts.AppSecretResponse;
import net.ufrog.leo.client.contracts.ResultCode;
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
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), AppResponse.class);
            }

            @Override
            public PageResponse<AppResponse> read() {
                //noinspection unchecked
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), PageResponse.class);
            }

            @Override
            public ListResponse<AppSecretResponse> readSecrets() {
                //noinspection unchecked
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), ListResponse.class);
            }
        };
    }
}

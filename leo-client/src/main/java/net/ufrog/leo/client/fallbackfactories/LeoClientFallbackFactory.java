package net.ufrog.leo.client.fallbackfactories;

import net.ufrog.aries.common.contract.ClientFallbackFactory;
import net.ufrog.aries.common.contract.ListResponse;
import net.ufrog.aries.common.contract.Response;
import net.ufrog.common.dict.Dicts;
import net.ufrog.leo.client.LeoClient;
import net.ufrog.leo.client.contracts.*;
import org.springframework.stereotype.Component;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-13
 * @since 3.0.0
 */
@Component
public class LeoClientFallbackFactory extends ClientFallbackFactory<LeoClient> {

    @Override
    public LeoClient getClientFallback() {
        return new LeoClient() {

            @Override
            public AppUserResponse getUser(String appId, String token) {
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), AppUserResponse.class);
            }

            @Override
            public ListResponse<AppResponse> getApps(String appId, String token) {
                //noinspection unchecked
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), ListResponse.class);
            }

            @Override
            public ListResponse<NavResponse> getNavs(String type, String parentId, String appId, String token) {
                //noinspection unchecked
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), ListResponse.class);
            }
        };
    }
}

package net.ufrog.leo.client.fallbackfactory;

import net.ufrog.aries.common.contract.ClientFallbackFactory;
import net.ufrog.aries.common.contract.ListResponse;
import net.ufrog.aries.common.contract.Response;
import net.ufrog.leo.client.LeoClient;
import net.ufrog.leo.client.contract.AppResponse;
import net.ufrog.leo.client.contract.AppUserResponse;
import net.ufrog.leo.client.contract.NavResponse;
import net.ufrog.leo.client.contract.ResultCode;
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
                return Response.createResp(ResultCode.NETWORK, AppUserResponse.class);
            }

            @Override
            public ListResponse<AppResponse> getApps(String appId, String token) {
                //noinspection unchecked
                return Response.createResp(ResultCode.NETWORK, ListResponse.class);
            }

            @Override
            public ListResponse<NavResponse> getNavs(String type, String parentId, String appId, String token) {
                //noinspection unchecked
                return Response.createResp(ResultCode.NETWORK, ListResponse.class);
            }
        };
    }
}

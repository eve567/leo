package net.ufrog.leo.client.fallbackfactories;

import net.ufrog.aries.common.contract.ClientFallbackFactory;
import net.ufrog.aries.common.contract.ListResponse;
import net.ufrog.aries.common.contract.Response;
import net.ufrog.common.dict.Dicts;
import net.ufrog.leo.client.GatewayClient;
import net.ufrog.leo.client.contracts.*;
import org.springframework.stereotype.Component;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 4.0.1, 2018-05-16
 * @since 4.0.1
 */
@Component
public class GatewayClientFallbackFactory extends ClientFallbackFactory<GatewayClient> {

    @Override
    public GatewayClient getClientFallback() {
        return new GatewayClient() {

            @Override
            public AppUserResponse authenticate(AuthenticateRequest authenticateRequest) {
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), AppUserResponse.class);
            }

            @Override
            public Response signOut(SignOutRequest signOutRequest) {
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), Response.class);
            }

            @Override
            public RedirectResponse getRedirectUrl(AccessTokenRequest accessTokenRequest) {
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), RedirectResponse.class);
            }

            @Override
            public ListResponse<AppResponse> findApps(String userId) {
                //noinspection unchecked
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), ListResponse.class);
            }

            @Override
            public Response checkForced(String userId) {
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), Response.class);
            }

            @Override
            public Response updatePassword(UpdatePasswordRequest updatePasswordRequest) {
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), Response.class);
            }
        };
    }
}

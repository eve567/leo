package net.ufrog.leo.client.fallbackfactories;

import net.ufrog.aries.common.contract.ClientFallbackFactory;
import net.ufrog.aries.common.contract.PageResponse;
import net.ufrog.aries.common.contract.Response;
import net.ufrog.common.dict.Dicts;
import net.ufrog.leo.client.UserClient;
import net.ufrog.leo.client.contracts.OpenPlatformRequest;
import net.ufrog.leo.client.contracts.ResultCode;
import net.ufrog.leo.client.contracts.UserRequest;
import net.ufrog.leo.client.contracts.UserResponse;
import org.springframework.stereotype.Component;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-13
 * @since 3.0.0
 */
@Component
public class UserClientFallbackFactory extends ClientFallbackFactory<UserClient> {

    @Override
    public UserClient getClientFallback() {
        return new UserClient() {

            @Override
            public UserResponse create(UserRequest userReq) {
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), UserResponse.class);
            }

            @Override
            public UserResponse read(String id) {
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), UserResponse.class);
            }

            @Override
            public PageResponse<UserResponse> read(UserRequest userReq) {
                //noinspection unchecked
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), PageResponse.class);
            }

            @Override
            public UserResponse update(String id, UserRequest userReq) {
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), UserResponse.class);
            }

            @Override
            public UserResponse updatePassword(String id, UserRequest userReq) {
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), UserResponse.class);
            }

            @Override
            public UserResponse freezeOrUnfreeze(String id) {
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), UserResponse.class);
            }

            @Override
            public UserResponse delete(String id) {
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), UserResponse.class);
            }

            @Override
            public UserResponse readOrCreateByOpenPlatform(OpenPlatformRequest openPlatformRequest) {
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), UserResponse.class);
            }

            @Override
            public Response registerOpenPlatform(OpenPlatformRequest openPlatformRequest) {
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), Response.class);
            }

            @Override
            public UserResponse readByCellphone(String cellphone) {
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), UserResponse.class);
            }

            @Override
            public UserResponse readByOpenPlatform(String[] keyValuePairs) {
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), UserResponse.class);
            }
        };
    }
}

package net.ufrog.leo.client.fallbackfactory;

import net.ufrog.aries.common.contract.ClientFallbackFactory;
import net.ufrog.aries.common.contract.PageResponse;
import net.ufrog.aries.common.contract.Response;
import net.ufrog.leo.client.UserClient;
import net.ufrog.leo.client.contract.ResultCode;
import net.ufrog.leo.client.contract.UserRequest;
import net.ufrog.leo.client.contract.UserResponse;
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
                return Response.createResp(ResultCode.NETWORK, UserResponse.class);
            }

            @Override
            public UserResponse read(String id) {
                return Response.createResp(ResultCode.NETWORK, UserResponse.class);
            }

            @Override
            public PageResponse<UserResponse> read(UserRequest userReq) {
                //noinspection unchecked
                return Response.createResp(ResultCode.NETWORK, PageResponse.class);
            }

            @Override
            public UserResponse update(String id, UserRequest userReq) {
                return Response.createResp(ResultCode.NETWORK, UserResponse.class);
            }

            @Override
            public UserResponse updatePassword(String id, UserRequest userReq) {
                return Response.createResp(ResultCode.NETWORK, UserResponse.class);
            }

            @Override
            public UserResponse freezeOrUnfreeze(String id) {
                return Response.createResp(ResultCode.NETWORK, UserResponse.class);
            }

            @Override
            public UserResponse delete(String id) {
                return Response.createResp(ResultCode.NETWORK, UserResponse.class);
            }
        };
    }
}

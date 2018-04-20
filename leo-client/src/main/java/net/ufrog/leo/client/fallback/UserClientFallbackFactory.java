package net.ufrog.leo.client.fallback;

import net.ufrog.aries.common.contract.ClientFallbackFactory;
import net.ufrog.aries.common.contract.PageResp;
import net.ufrog.leo.client.UserClient;
import net.ufrog.leo.client.contract.ResultCode;
import net.ufrog.leo.client.contract.UserReq;
import net.ufrog.leo.client.contract.UserResp;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-13
 * @since 3.0.0
 */
@Component
public class UserClientFallbackFactory extends ClientFallbackFactory<UserClient> {

    @Override
    public UserClient getClientFallback() {
        return new UserClientFallback();
    }

    /**
     * @author ultrafrog, ufrog.net@gmail.com
     * @version 3.0.0, 2018-04-13
     * @since 3.0.0
     */
    static class UserClientFallback implements UserClient {

        @Override
        public UserResp create(UserReq userReq) {
            return createNetworkErrorResp();
        }

        @Override
        public UserResp read(String id) {
            return createNetworkErrorResp();
        }

        @Override
        public PageResp<UserResp> read(UserReq userReq) {
            PageResp<UserResp> prUserResp = new PageResp<>(0L, 0, 0, Collections.emptyList());
            prUserResp.setResultCode(ResultCode.NETWORK);
            return prUserResp;
        }

        @Override
        public UserResp update(String id, UserReq userReq) {
            return createNetworkErrorResp();
        }

        @Override
        public UserResp updatePassword(String id, UserReq userReq) {
            return createNetworkErrorResp();
        }

        @Override
        public UserResp freezeOrUnfreeze(String id) {
            return createNetworkErrorResp();
        }

        @Override
        public UserResp delete(String id) {
            return createNetworkErrorResp();
        }

        /**
         * @return 网络异常响应
         */
        private UserResp createNetworkErrorResp() {
            UserResp userResp = new UserResp();
            userResp.setResultCode(ResultCode.NETWORK);
            return userResp;
        }
    }
}

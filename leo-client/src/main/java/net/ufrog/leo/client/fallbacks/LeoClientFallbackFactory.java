package net.ufrog.leo.client.fallbacks;

import feign.hystrix.FallbackFactory;
import net.ufrog.common.Logger;
import net.ufrog.leo.client.LeoClient;
import net.ufrog.leo.client.contract.AppUserResp;
import net.ufrog.leo.client.contract.ResultCode;
import org.springframework.stereotype.Component;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-13
 * @since 3.0.0
 */
@Component
public class LeoClientFallbackFactory implements FallbackFactory<LeoClient> {

    @Override
    public LeoClient create(Throwable throwable) {
        Logger.warn("fallback, reason was: {}", throwable.getMessage());
        return new LeoClientFallback();
    }

    /**
     * @author ultrafrog, ufrog.net@gmail.com
     * @version 3.0.0, 2018-04-13
     * @since 3.0.0
     */
    class LeoClientFallback implements LeoClient {

        @Override
        public AppUserResp getUser(String appId, String token) {
            AppUserResp appUserResp = new AppUserResp();
            appUserResp.setResultCode(ResultCode.NETWORK);
            return appUserResp;
        }
    }
}

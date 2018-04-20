package net.ufrog.leo.client.fallback;

import net.ufrog.aries.common.contract.ClientFallbackFactory;
import net.ufrog.aries.common.contract.ListResp;
import net.ufrog.leo.client.LeoClient;
import net.ufrog.leo.client.contract.AppResp;
import net.ufrog.leo.client.contract.AppUserResp;
import net.ufrog.leo.client.contract.NavResp;
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
        return new LeoClientFallback();
    }

    /**
     * @author ultrafrog, ufrog.net@gmail.com
     * @version 3.0.0, 2018-04-13
     * @since 3.0.0
     */
    static class LeoClientFallback implements LeoClient {

        @Override
        public AppUserResp getUser(String appId, String token) {
            AppUserResp appUserResp = new AppUserResp();
            appUserResp.setResultCode(ResultCode.NETWORK);
            return appUserResp;
        }

        @Override
        public ListResp<AppResp> getApps(String appId, String token) {
            ListResp<AppResp> lrAppResp = new ListResp<>();
            lrAppResp.setResultCode(ResultCode.NETWORK);
            return lrAppResp;
        }

        @Override
        public ListResp<NavResp> getNavs(String type, String parentId, String appId, String token) {
            ListResp<NavResp> lrNavResp = new ListResp<>();
            lrNavResp.setResultCode(ResultCode.NETWORK);
            return lrNavResp;
        }
    }
}

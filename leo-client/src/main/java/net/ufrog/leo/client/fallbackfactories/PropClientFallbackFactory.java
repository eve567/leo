package net.ufrog.leo.client.fallbackfactories;

import net.ufrog.aries.common.contract.ClientFallbackFactory;
import net.ufrog.aries.common.contract.ListResponse;
import net.ufrog.aries.common.contract.Response;
import net.ufrog.common.dict.Dicts;
import net.ufrog.leo.client.PropClient;
import net.ufrog.leo.client.contracts.PropResponse;
import net.ufrog.leo.client.contracts.ResultCode;
import org.springframework.stereotype.Component;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-05-15
 * @since 3.0.0
 */
@Component
public class PropClientFallbackFactory extends ClientFallbackFactory<PropClient> {

    @Override
    public PropClient getClientFallback() {
        return new PropClient() {

            @Override
            public ListResponse<PropResponse> readAll() {
                //noinspection unchecked
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), ListResponse.class);
            }

            @Override
            public PropResponse update(String id) {
                return Response.createResponse(ResultCode.NETWORK, Dicts.name(ResultCode.NETWORK, ResultCode.class), PropResponse.class);
            }
        };
    }
}

package net.ufrog.leo.client;

import io.swagger.annotations.Api;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户业务客户端
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-03-27
 * @since 3.0.0
 */
@FeignClient(name = "leo-server")
@RequestMapping("/user")
@Api(value = "用户服务")
public interface UserClient {



    /**
     * @author ultrafrog, ufrog.net@gmail.com
     * @version 3.0.0, 2018-04-07
     * @since 3.0.0
     */
    @Component
    class UserClientFallback implements UserClient {

    }
}

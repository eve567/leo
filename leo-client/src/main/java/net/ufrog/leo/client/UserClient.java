package net.ufrog.leo.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户业务客户端
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2018-03-27
 * @since 0.1
 */
@FeignClient(name = "leo-server")
@RequestMapping("/user")
@RestController
public interface UserClient {


}

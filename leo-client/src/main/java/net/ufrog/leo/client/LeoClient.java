package net.ufrog.leo.client;

import io.swagger.annotations.Api;
import net.ufrog.leo.client.contract.AppUserResp;
import net.ufrog.leo.client.fallbacks.LeoClientFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户中心服务
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-11
 * @since 3.0.0
 */
@FeignClient(name = "leo-server", fallbackFactory = LeoClientFallbackFactory.class)
@RequestMapping("/api")
@Api(value = "用户中心服务")
public interface LeoClient {

    /**
     * 获取当前用户
     *
     * @param appId 应用编号
     * @param token 令牌
     * @return 应用用户响应
     */
    @RequestMapping(value = "/user/{appId}/{token}", method = RequestMethod.GET)
    AppUserResp getUser(@PathVariable("appId") String appId, @PathVariable("token") String token);
}

package net.ufrog.leo.client;

import io.swagger.annotations.Api;
import net.ufrog.aries.common.contract.ListResp;
import net.ufrog.leo.client.contract.AppResp;
import net.ufrog.leo.client.contract.AppUserResp;
import net.ufrog.leo.client.contract.NavResp;
import net.ufrog.leo.client.fallback.LeoClientFallbackFactory;
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

    /**
     * 获取当前用户权限下的应用
     *
     * @param appId 应用编号
     * @param token 令牌
     * @return 应用列表
     */
    @RequestMapping(value = "/apps/{token}/{appId}")
    ListResp<AppResp> getApps(@PathVariable("appId") String appId, @PathVariable("token") String token);

    /**
     * 获取当前用户当前应用下的导航
     *
     * @param type 类型
     * @param parentId 上级编号
     * @param appId 应用编号
     * @param token 令牌
     * @return 导航列表
     */
    @RequestMapping(value = "/navs/{type}/{parentId}/{token}/{appId}", method = RequestMethod.GET)
    ListResp<NavResp> getNavs(@PathVariable("type") String type, @PathVariable("parentId") String parentId, @PathVariable("appId") String appId, @PathVariable("token") String token);
}

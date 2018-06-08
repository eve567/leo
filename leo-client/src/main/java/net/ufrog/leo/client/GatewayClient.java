package net.ufrog.leo.client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.ufrog.aries.common.contract.ListResponse;
import net.ufrog.aries.common.contract.Response;
import net.ufrog.leo.client.contracts.*;
import net.ufrog.leo.client.fallbackfactories.GatewayClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 4.0.1, 2018-05-16
 * @since 4.0.1
 */
@FeignClient(value = Client.APP_NAME, fallbackFactory = GatewayClientFallbackFactory.class)
@RequestMapping(value = "/gateway")
@Api(value = "网关专用客户端")
public interface GatewayClient {

    /**
     * 鉴权认证
     *
     * @param authenticateRequest 鉴权认证请求
     * @return 用户响应
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ApiOperation(value = "通过账户密码进行鉴权认证")
    AppUserResponse authenticate(@RequestBody AuthenticateRequest authenticateRequest);

    /**
     * 注销
     *
     * @param signOutRequest 注销请求
     * @return 处理结果
     */
    @RequestMapping(value = "/sign_out", method = RequestMethod.POST)
    @ApiOperation(value = "注销登录状态")
    Response signOut(@RequestBody SignOutRequest signOutRequest);

    /**
     * 获取跳转地址
     *
     * @param accessTokenRequest 访问令牌请求
     * @return 跳转响应
     */
    @RequestMapping(value = "/get_redirect_url", method = RequestMethod.POST)
    @ApiOperation(value = "获取访问令牌并返回跳转地址")
    RedirectResponse getRedirectUrl(@RequestBody AccessTokenRequest accessTokenRequest);

    /**
     * 查询所有应用
     *
     * @param userId 用户编号
     * @return 应用响应列表
     */
    @RequestMapping(value = "/apps/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "查询当前用户权限下的应用列表")
    ListResponse<AppResponse> findApps(@PathVariable("userId") String userId);

    /**
     * 检查是否需要强制修改密码
     *
     * @param userId 用户编号
     * @return 判断响应
     */
    @RequestMapping(value = "/check_forced/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "检查用户是否需要强制修改密码")
    Response checkForced(@PathVariable("userId") String userId);

    /**
     * 更新用户密码
     *
     * @param updatePasswordRequest 更新用户密码请求
     * @return 更新响应
     */
    @RequestMapping(value = "/update_password", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户密码")
    Response updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest);
}

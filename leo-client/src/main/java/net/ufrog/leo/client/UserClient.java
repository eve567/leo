package net.ufrog.leo.client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.ufrog.aries.common.contract.PageResp;
import net.ufrog.leo.client.contract.UserReq;
import net.ufrog.leo.client.contract.UserResp;
import net.ufrog.leo.client.fallback.UserClientFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户业务客户端
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-03-27
 * @since 3.0.0
 */
@FeignClient(name = "leo-server", fallbackFactory = UserClientFallbackFactory.class)
@RequestMapping("/users")
@Api(value = "用户服务")
public interface UserClient {

    /**
     * @param userReq 用户请求
     * @return 用户响应
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "用户创建", notes = "创建新用户")
    UserResp create(@RequestBody UserReq userReq);

    /**
     * @param id 编号
     * @return 用户响应
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "用户查询", notes = "查询指定用户信息")
    UserResp read(@PathVariable("id") String id);

    /**
     * @param userReq 用户请求
     * @return 用户列表
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "用户查询", notes = "查询用户信息")
    PageResp<UserResp> read(UserReq userReq);

    /**
     * @param id 编号
     * @param userReq 用户请求
     * @return 用户响应
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "用户更新", notes = "更新用户信息")
    UserResp update(@PathVariable("id") String id, @RequestBody UserReq userReq);

    /**
     * @param id 编号
     * @param userReq 用户请求
     * @return 用户响应
     */
    @RequestMapping(value = "/password/{id}", method = RequestMethod.PATCH)
    @ApiOperation(value = "更新用户登录密码", notes = "更新登录密码")
    UserResp updatePassword(@PathVariable("id") String id, @RequestBody UserReq userReq);

    /**
     * @param id 编号
     * @return 用户响应
     */
    @RequestMapping(value = "freeze_or_unfreeze/{id}", method = RequestMethod.PATCH)
    @ApiOperation(value = "冻结或解冻用户", notes = "根据当前状态冻结或者解冻")
    UserResp freezeOrUnfreeze(@PathVariable("id") String id);

    /**
     * @param id 编号
     * @return 用户响应
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "用户删除", notes = "删除用户信息")
    UserResp delete(@PathVariable("id") String id);
}

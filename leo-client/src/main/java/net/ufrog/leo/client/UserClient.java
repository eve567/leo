package net.ufrog.leo.client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.ufrog.aries.common.contract.PageResponse;
import net.ufrog.aries.common.contract.Response;
import net.ufrog.leo.client.contracts.OpenPlatformRequest;
import net.ufrog.leo.client.contracts.UserRequest;
import net.ufrog.leo.client.contracts.UserResponse;
import net.ufrog.leo.client.fallbackfactories.UserClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
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
@FeignClient(name = Client.NAME, fallbackFactory = UserClientFallbackFactory.class)
@RequestMapping("/users")
@Api(value = "用户服务")
public interface UserClient {

    /**
     * @param userRequest 用户请求
     * @return 用户响应
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "用户创建", notes = "创建新用户")
    UserResponse create(@RequestBody UserRequest userRequest);

    /**
     * @param id 编号
     * @return 用户响应
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "用户查询", notes = "查询指定用户信息")
    UserResponse read(@PathVariable("id") String id);

    /**
     * @param userRequest 用户请求
     * @return 用户列表
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "用户查询", notes = "查询用户信息")
    PageResponse<UserResponse> read(UserRequest userRequest);

    /**
     * @param id 编号
     * @param userRequest 用户请求
     * @return 用户响应
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "用户更新", notes = "更新用户信息")
    UserResponse update(@PathVariable("id") String id, @RequestBody UserRequest userRequest);

    /**
     * @param id 编号
     * @param userRequest 用户请求
     * @return 用户响应
     */
    @RequestMapping(value = "/password/{id}", method = RequestMethod.PATCH)
    @ApiOperation(value = "更新用户登录密码", notes = "更新登录密码")
    UserResponse updatePassword(@PathVariable("id") String id, @RequestBody UserRequest userRequest);

    /**
     * @param id 编号
     * @return 用户响应
     */
    @RequestMapping(value = "freeze_or_unfreeze/{id}", method = RequestMethod.PATCH)
    @ApiOperation(value = "冻结或解冻用户", notes = "根据当前状态冻结或者解冻")
    UserResponse freezeOrUnfreeze(@PathVariable("id") String id);

    /**
     * @param id 编号
     * @return 用户响应
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "用户删除", notes = "删除用户信息")
    UserResponse delete(@PathVariable("id") String id);

    /**
     * @param openPlatformRequest 开放平台请求
     * @return 用户响应
     */
    @RequestMapping(value = "/read_or_create_by_open_platform", method = RequestMethod.POST)
    @ApiOperation(value = "查询或创建开放平台用户", notes = "通过开发平台标识查询用户 若不存在则创建新用户")
    UserResponse readOrCreateByOpenPlatform(@RequestBody OpenPlatformRequest openPlatformRequest);

    /**
     * @param openPlatformRequest 开放平台请求
     * @return 响应
     */
    @RequestMapping(value = "/register_open_platform", method = RequestMethod.POST)
    @ApiOperation(value = "注册用户开放平台信息", notes = "将开放平台标识注册给用户")
    Response registerOpenPlatform(@RequestBody OpenPlatformRequest openPlatformRequest);
}

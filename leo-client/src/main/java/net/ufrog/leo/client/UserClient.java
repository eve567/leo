package net.ufrog.leo.client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.ufrog.aries.common.contract.PageResp;
import net.ufrog.leo.client.contract.ResultCode;
import net.ufrog.leo.client.contract.UserReq;
import net.ufrog.leo.client.contract.UserResp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;

/**
 * 用户业务客户端
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-03-27
 * @since 3.0.0
 */
@FeignClient(name = "leo-server", fallback = UserClient.UserClientFallback.class)
@RequestMapping("/user")
@Api(value = "用户服务")
public interface UserClient {

    /**
     * @param userReq 用户请求
     * @return 用户响应
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "用户创建", notes = "创建新用户")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "名称", name = "name", paramType = "body", dataTypeClass = String.class),
            @ApiImplicitParam(value = "账号", name = "account", paramType = "body", dataTypeClass = String.class),
            @ApiImplicitParam(value = "手机号码", name = "cellphone", paramType = "body", dataTypeClass = String.class),
            @ApiImplicitParam(value = "电子邮件", name = "email", paramType = "body", dataTypeClass = String.class),
            @ApiImplicitParam(value = "密码", name = "password", paramType = "body", dataTypeClass = String.class),
            @ApiImplicitParam(value = "类型", name = "type", required = true, paramType = "body", dataTypeClass = String.class, allowableValues = "00,90")
    })
    UserResp create(@RequestBody UserReq userReq);

    /**
     * @param userReq 用户请求
     * @return 用户列表
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "用户查询", notes = "查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "编号 - 如果不为空则查询单个用户，最高优先级", name = "id", paramType = "body", dataTypeClass = String.class),
            @ApiImplicitParam(value = "账号 - 如果不为空则按账号查询，模糊查询", name = "account", paramType = "body", dataTypeClass = String.class),
            @ApiImplicitParam(value = "手机号码 - 如果不为空则按手机号码查询，模糊查询", name = "cellphone", paramType = "body", dataTypeClass = String.class),
            @ApiImplicitParam(value = "电子邮件 - 如果不为空则按电子邮件查询，模糊查询", name = "cellphone", paramType = "body", dataTypeClass = String.class),
            @ApiImplicitParam(value = "名称 - 如果不为空则按名称查询，模糊查询", name = "name", paramType = "body", dataTypeClass = String.class)
    })
    PageResp<UserResp> read(@RequestBody UserReq userReq);

    /**
     * @param userReq 用户请求
     * @return 用户响应
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ApiOperation(value = "用户更新", notes = "更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "编号 - 待更新用户编号", name = "id", paramType = "body", dataTypeClass = String.class),
            @ApiImplicitParam(value = "名称", name = "name", paramType = "body", dataTypeClass = String.class),
            @ApiImplicitParam(value = "账号", name = "account", paramType = "body", dataTypeClass = String.class),
            @ApiImplicitParam(value = "手机号码", name = "cellphone", paramType = "body", dataTypeClass = String.class),
            @ApiImplicitParam(value = "电子邮件", name = "email", paramType = "body", dataTypeClass = String.class),
            @ApiImplicitParam(value = "类型", name = "type", required = true, paramType = "body", dataTypeClass = String.class, allowableValues = "00,90")
    })
    UserResp update(@RequestBody UserReq userReq);

    /**
     * @param userReq 用户请求
     * @return 用户响应
     */
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ApiOperation(value = "用户删除", notes = "删除用户信息")
    @ApiImplicitParam(value = "编号 - 待删除用户编号", name = "id", required = true, paramType = "body", dataTypeClass = String.class)
    UserResp delete(@RequestBody UserReq userReq);

    /**
     * @author ultrafrog, ufrog.net@gmail.com
     * @version 3.0.0, 2018-04-07
     * @since 3.0.0
     */
    @Component
    class UserClientFallback implements UserClient {

        @Override
        public UserResp create(UserReq userReq) {
            UserResp userResp = new UserResp();
            userResp.setResultCode(ResultCode.NETWORK);
            return userResp;
        }

        @Override
        public PageResp<UserResp> read(UserReq userReq) {
            PageResp<UserResp> prUserResp = new PageResp<>(0L, 0, 0, Collections.emptyList());
            prUserResp.setResultCode(ResultCode.NETWORK);
            return prUserResp;
        }

        @Override
        public UserResp update(UserReq userReq) {
            UserResp userResp = new UserResp();
            userResp.setResultCode(ResultCode.NETWORK);
            return userResp;
        }

        @Override
        public UserResp delete(UserReq userReq) {
            UserResp userResp = new UserResp();
            userResp.setResultCode(ResultCode.NETWORK);
            return userResp;
        }
    }
}

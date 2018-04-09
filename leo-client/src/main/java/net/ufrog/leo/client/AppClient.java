package net.ufrog.leo.client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.ufrog.aries.common.contract.ListResp;
import net.ufrog.leo.client.contract.AppResp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用业务客户端
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2018-03-27
 * @since 0.1
 */
@FeignClient(name = "leo-server")
@RequestMapping("/app")
@Api(value = "用户服务")
public interface AppClient {

    /**
     * 查询应用
     *
     * @param id 编号
     * @return 单个应用响应
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "按编号查询应用", notes = "查询单个应用")
    @ApiImplicitParam(value = "应用编号", name = "id", required = true, paramType = "path", dataTypeClass = String.class)
    AppResp findById(@PathVariable("id") String id);

    /**
     * 查询所有应用
     *
     * @return 应用列表
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有应用", notes = "查询单个应用")
    ListResp<AppResp> findAll();
}

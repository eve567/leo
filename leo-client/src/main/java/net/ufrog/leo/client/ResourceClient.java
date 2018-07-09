package net.ufrog.leo.client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.ufrog.aries.common.contract.ListResponse;
import net.ufrog.leo.client.contracts.ResourceRequest;
import net.ufrog.leo.client.contracts.ResourceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 资源服务客户端
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 5.0.0, 2018-07-09
 * @since 5.0.0
 */
@FeignClient(name = Client.NAME)
@RequestMapping(value = "/resources")
@Api(value = "资源服务")
public interface ResourceClient {

    /**
     * 读取资源列表
     *
     * @param type 类型
     * @return 资源响应列表
     */
    @RequestMapping(value = "/{type}", method = RequestMethod.GET)
    @ApiOperation(value = "读取资源列表", notes = "响应当前用户权限下相关类型的资源")
    ListResponse<ResourceResponse> read(@PathVariable("type") String type);

    /**
     * 创建资源
     *
     * @param resourceRequest 资源请求
     * @return 资源响应
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "创建资源信息", notes = "各系统将业务数据提交到用户中心以便于权限管理")
    ResourceResponse create(@RequestBody ResourceRequest resourceRequest);
}

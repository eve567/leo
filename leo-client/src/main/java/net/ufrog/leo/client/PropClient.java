package net.ufrog.leo.client;

import net.ufrog.aries.common.contract.ListResponse;
import net.ufrog.leo.client.contracts.PropResponse;
import net.ufrog.leo.client.fallbackfactories.PropClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 4.0.0, 2018-05-15
 * @since 4.0.0
 */
@FeignClient(value = Client.APP_NAME, fallbackFactory = PropClientFallbackFactory.class)
@RequestMapping(value = "/props")
public interface PropClient {

    /**
     * 查询所有属性
     *
     * @return 属性列表
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    ListResponse<PropResponse> readAll();

    /**
     * 更新属性
     *
     * @param id 编号
     * @return 更新后属性
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    PropResponse update(@PathVariable("id") String id);
}

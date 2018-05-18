package net.ufrog.leo.server.controllers;

import net.ufrog.aries.common.contract.ListResponse;
import net.ufrog.leo.client.PropClient;
import net.ufrog.leo.client.contracts.PropResponse;
import net.ufrog.leo.domain.models.Prop;
import net.ufrog.leo.service.PropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-05-15
 * @since 3.0.0
 */
@RestController
public class PropController implements PropClient {

    /** 系统属性业务接口 */
    private final PropService propService;

    /**
     * 构造函数
     *
     * @param propService 系统属性业务接口
     */
    @Autowired
    public PropController(PropService propService) {
        this.propService = propService;
    }

    @Override
    public ListResponse<PropResponse> readAll() {
        List<PropResponse> lPropResponse = propService.findAll().stream().map(PropController::toPropResponse).collect(Collectors.toList());
        return new ListResponse<>(lPropResponse);
    }

    @Override
    public PropResponse update(String id) {
        //TODO
        return null;
    }

    /**
     * 转换成属性响应
     *
     * @param prop 属性模型
     * @return 属性响应
     */
    private static PropResponse toPropResponse(Prop prop) {
        PropResponse propResponse = new PropResponse();
        propResponse.setId(prop.getId());
        propResponse.setCode(prop.getCode());
        propResponse.setValue(prop.getValue());
        return propResponse;
    }
}

package net.ufrog.leo.server.controllers;

import net.ufrog.aries.common.contract.PageResponse;
import net.ufrog.leo.client.AppClient;
import net.ufrog.leo.client.contracts.AppResponse;
import net.ufrog.leo.domain.models.App;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * 应用控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-11
 * @since 3.0.0
 */
@RestController
@CrossOrigin
public class AppController implements AppClient {

    @Override
    public AppResponse read(String id) {
        return null;
    }

    @Override
    public PageResponse<AppResponse> read() {
        return null;
    }

    /**
     * 转换陈应用响应
     *
     * @param app 应用模型
     * @return 应用响应
     */
    public static AppResponse toAppResponse(@NotNull App app) {
        AppResponse appResponse = new AppResponse();

        appResponse.setId(app.getId());
        appResponse.setName(app.getName());
        appResponse.setCode(app.getCode());
        appResponse.setColor(app.getColor());
        appResponse.setLogo(app.getLogo());
        return appResponse;
    }
}

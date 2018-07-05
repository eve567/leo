package net.ufrog.leo.server.controllers;

import net.ufrog.aries.common.contract.ListResponse;
import net.ufrog.aries.common.contract.PageResponse;
import net.ufrog.leo.client.AppClient;
import net.ufrog.leo.client.contracts.AppResponse;
import net.ufrog.leo.client.contracts.AppSecretResponse;
import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.service.AppService;
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

    /** 应用业务接口 */
    private final AppService appService;

    /**
     * 构造函数
     *
     * @param appService 应用业务接口
     */
    public AppController(AppService appService) {
        this.appService = appService;
    }

    @Override
    public AppResponse read(String id) {
        return null;
    }

    @Override
    public PageResponse<AppResponse> read() {
        return null;
    }

    @Override
    public ListResponse<AppSecretResponse> readSecrets() {
        ListResponse<AppSecretResponse> lrAppSecretResponse = new ListResponse<>();
        appService.findAll().forEach(app -> lrAppSecretResponse.add(new AppSecretResponse(app.getId(), app.getSecret())));
        return lrAppSecretResponse;
    }

    /**
     * 转换陈应用响应
     *
     * @param app 应用模型
     * @return 应用响应
     */
    static AppResponse toAppResponse(@NotNull App app) {
        AppResponse appResponse = new AppResponse();

        appResponse.setId(app.getId());
        appResponse.setName(app.getName());
        appResponse.setCode(app.getCode());
        appResponse.setColor(app.getColor());
        appResponse.setLogo(app.getLogo());
        return appResponse;
    }
}

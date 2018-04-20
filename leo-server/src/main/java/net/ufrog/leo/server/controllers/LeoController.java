package net.ufrog.leo.server.controllers;

import net.ufrog.aries.common.contract.ListResp;
import net.ufrog.common.Logger;
import net.ufrog.leo.client.LeoClient;
import net.ufrog.leo.client.app.LeoAppUser;
import net.ufrog.leo.client.contract.AppResp;
import net.ufrog.leo.client.contract.AppUserResp;
import net.ufrog.leo.client.contract.NavResp;
import net.ufrog.leo.client.contract.ResultCode;
import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.domain.models.Nav;
import net.ufrog.leo.server.accesstoken.AccessToken;
import net.ufrog.leo.server.accesstoken.AccessTokenManager;
import net.ufrog.leo.service.AppService;
import net.ufrog.leo.service.NavService;
import net.ufrog.leo.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-13
 * @since 3.0.0
 */
@SuppressWarnings("MVCPathVariableInspection")
@RestController
@CrossOrigin
public class LeoController implements LeoClient {

    /** 应用业务接口 */
    private AppService appService;

    /** 导航业务接口 */
    private NavService navService;

    /** 权限业务接口 */
    private SecurityService securityService;

    /**
     * 构造函数
     *
     * @param appService 应用业务接口
     * @param navService 导航业务接口
     * @param securityService 权限业务接口
     */
    @Autowired
    public LeoController(AppService appService, NavService navService, SecurityService securityService) {
        this.appService = appService;
        this.navService = navService;
        this.securityService = securityService;
    }

    @Override
    public AppUserResp getUser(@PathVariable("appId") String appId, @PathVariable("token") String token) {
        Logger.debug("find user by appId: {}, token: {}", appId, token);
        AccessToken accessToken = AccessTokenManager.get().get(token, appId);
        AppUserResp appUserResp = new AppUserResp();

        if (accessToken != null) {
            LeoAppUser leoAppUser = accessToken.getLeoAppUser();

            Logger.debug("find user: {}", leoAppUser.getId());
            appUserResp.setResultCode(ResultCode.SUCCESS);
            appUserResp.setId(leoAppUser.getId());
            appUserResp.setAccount(leoAppUser.getAccount());
            appUserResp.setName(leoAppUser.getName());
            appUserResp.setToken(leoAppUser.getToken());
        } else {
            Logger.debug("cannot find user by appId: {}, token: {}", appId, token);
            appUserResp.setResultCode(ResultCode.NOT_SIGN);
        }
        return appUserResp;
    }

    @Override
    public ListResp<AppResp> getApps(@PathVariable("appId") String appId, @PathVariable("token") String token) {
        AccessToken accessToken = AccessTokenManager.get().get(token, appId);
        ListResp<AppResp> lrAppResp = new ListResp<>();

        securityService.filter(appService.findAll(), accessToken.getUserId()).forEach(app -> {
            AppResp appResp = new AppResp();
            appResp.setId(app.getId());
            appResp.setName(app.getName());
            appResp.setCode(app.getCode());
            appResp.setLogo(app.getLogo());
            appResp.setColor(app.getColor());
            lrAppResp.getContent().add(appResp);
        });
        return lrAppResp;
    }

    @Override
    public ListResp<NavResp> getNavs(@PathVariable("type") String type, @PathVariable("parentId") String parentId, @PathVariable("appId") String appId, @PathVariable("token") String token) {
        AccessToken accessToken = AccessTokenManager.get().get(token, appId);
        List<Nav> lNav = navService.getChildren(type, appId, parentId);
        ListResp<NavResp> lrNavResp = new ListResp<>();
        App app = appService.getById(appId);

        securityService.filter(lNav, accessToken.getUserId()).forEach(nav -> {
            NavResp navResp = new NavResp();
            navResp.setId(nav.getId());
            navResp.setName(nav.getName());
            navResp.setSubname(nav.getSubname());
            navResp.setCode(nav.getCode());
            navResp.setUrl(nav.getPath().startsWith("@") ? nav.getPath().replace("@", app.getUrl()) : nav.getPath());
            navResp.setTarget(nav.getTarget());
            lrNavResp.getContent().add(navResp);
        });
        return lrNavResp;
    }
}

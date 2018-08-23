package net.ufrog.leo.server.controllers;

import net.ufrog.aries.common.contract.ListResponse;
import net.ufrog.common.Logger;
import net.ufrog.leo.client.LeoClient;
import net.ufrog.leo.client.app.LeoAppUser;
import net.ufrog.leo.client.contracts.*;
import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.domain.models.Nav;
import net.ufrog.leo.domain.models.ViewItem;
import net.ufrog.leo.server.accesstoken.AccessToken;
import net.ufrog.leo.server.accesstoken.AccessTokenManager;
import net.ufrog.leo.service.AppService;
import net.ufrog.leo.service.NavService;
import net.ufrog.leo.service.SecurityService;
import net.ufrog.leo.service.ViewService;
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
    private final AppService appService;

    /** 导航业务接口 */
    private final NavService navService;

    /** 权限业务接口 */
    private final SecurityService securityService;

    /** 视图业务接口 */
    private final ViewService viewService;

    /**
     * 构造函数
     *  @param appService 应用业务接口
     * @param navService 导航业务接口
     * @param securityService 权限业务接口
     * @param viewService 视图业务接口
     */
    @Autowired
    public LeoController(AppService appService, NavService navService, SecurityService securityService, ViewService viewService) {
        this.appService = appService;
        this.navService = navService;
        this.securityService = securityService;
        this.viewService = viewService;
    }

    @Override
    public AppUserResponse getUser(@PathVariable("appId") String appId, @PathVariable("token") String token) {
        Logger.debug("find user by appId: %s, token: %s", appId, token);
        AccessToken accessToken = AccessTokenManager.get().get(token, appId);
        AppUserResponse appUserResp = new AppUserResponse();

        if (accessToken != null) {
            LeoAppUser leoAppUser = accessToken.getLeoAppUser();

            Logger.debug("find user: %s", leoAppUser.getId());
            appUserResp.setResultCode(ResultCode.SUCCESS);
            appUserResp.setId(leoAppUser.getId());
            appUserResp.setAccount(leoAppUser.getAccount());
            appUserResp.setName(leoAppUser.getName());
            appUserResp.setToken(leoAppUser.getToken());
        } else {
            Logger.debug("cannot find user by appId: %s, token: %s", appId, token);
            appUserResp.setResultCode(ResultCode.NOT_SIGN);
        }
        return appUserResp;
    }

    @Override
    public ListResponse<AppResponse> getApps(@PathVariable("appId") String appId, @PathVariable("token") String token) {
        AccessToken accessToken = AccessTokenManager.get().get(token, appId);
        ListResponse<AppResponse> lrAppResp = new ListResponse<>();

        securityService.filter(appService.findAll(), accessToken.getUserId()).forEach(app -> {
            AppResponse appResp = new AppResponse();
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
    public ListResponse<NavResponse> getNavs(@PathVariable("type") String type, @PathVariable("parentId") String parentId, @PathVariable("appId") String appId, @PathVariable("token") String token) {
        AccessToken accessToken = AccessTokenManager.get().get(token, appId);
        List<Nav> lNav = navService.getChildren(type, appId, parentId);
        ListResponse<NavResponse> lrNavResp = new ListResponse<>();
        App app = appService.getById(appId);

        securityService.filter(lNav, accessToken.getUserId()).forEach(nav -> {
            NavResponse navResp = new NavResponse();
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

    @Override
    public ListResponse<ViewItemResponse> getViewItems(@PathVariable("code") String code, @PathVariable("token") String token, @PathVariable("appId") String appId) {
        AccessToken accessToken = AccessTokenManager.get().get(token, appId);
        List<ViewItem> lViewItem = viewService.readItemsByViewCode(appId, code);
        ListResponse<ViewItemResponse> lrViewItemResponse = new ListResponse<>();

        securityService.filter(lViewItem, accessToken.getUserId()).forEach(viewItem -> {
            ViewItemResponse viewItemResponse = new ViewItemResponse();
            viewItemResponse.setId(viewItem.getId());
            viewItemResponse.setName(viewItem.getName());
            viewItemResponse.setCode(viewItem.getCode());
            lrViewItemResponse.add(viewItemResponse);
        });
        return lrViewItemResponse;
    }
}

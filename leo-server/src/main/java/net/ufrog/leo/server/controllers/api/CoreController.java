package net.ufrog.leo.server.controllers.api;

import net.ufrog.common.Logger;
import net.ufrog.common.ThreadPools;
import net.ufrog.common.utils.Strings;
import net.ufrog.common.web.app.WebApp;
import net.ufrog.leo.client.LeoAppUser;
import net.ufrog.leo.client.api.APIs;
import net.ufrog.leo.client.api.beans.*;
import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.domain.models.Nav;
import net.ufrog.leo.domain.models.User;
import net.ufrog.leo.domain.models.UserSignLog;
import net.ufrog.leo.server.AccessToken;
import net.ufrog.leo.server.AccessTokenManager;
import net.ufrog.leo.server.LeoException;
import net.ufrog.leo.service.AppService;
import net.ufrog.leo.service.NavService;
import net.ufrog.leo.service.SecurityService;
import net.ufrog.leo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 核心控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-04-22
 * @since 0.1
 */
@RestController
@RequestMapping("/api")
public class CoreController {

    private static final String NAV_PREFIX  = "@";

    /** 应用业务接口 */
    private final AppService appService;

    /** 导航业务接口 */
    private final NavService navService;

    /** 权限业务接口 */
    private final SecurityService securityService;

    /** 用户业务接口 */
    private final UserService userService;

    /**
     * 构造函数
     *
     * @param appService 应用业务接口
     * @param navService 导航业务接口
     * @param securityService 权限业务接口
     * @param userService 用户业务接口
     */
    @Autowired
    public CoreController(AppService appService, NavService navService, SecurityService securityService, UserService userService) {
        this.appService = appService;
        this.navService = navService;
        this.securityService = securityService;
        this.userService = userService;
    }

    /**
     * 读取当前用户
     *
     * @param token 令牌
     * @param appId 应用编号
     * @return 当前应用用户
     */
    @GetMapping("/user/{token}/{appId}")
    public AppUserResp getUser(@PathVariable("token") String token, @PathVariable("appId") String appId) {
        LeoAppUser leoAppUser = AccessTokenManager.get().get(token, appId).getAppUser();
        return new AppUserResp(leoAppUser.getId(), leoAppUser.getAccount(), leoAppUser.getName(), leoAppUser.getToken());
    }

    /**
     * 通过开放平台代码读取用户
     *
     * @param request 请求
     * @return 用户信息
     */
    @PostMapping("/user_open_platform")
    public AppUserResp getUserByOpenPlatformCode(HttpServletRequest request) {
        OpenPlatformUserReq openPlatformUserReq = APIs.parseBytes(net.ufrog.common.app.App.current(WebApp.class).getBody(), OpenPlatformUserReq.class);
        User user = userService.findOrCreateByOpenPlatform(openPlatformUserReq);

        if (user != null) {
            App app = appService.findById(openPlatformUserReq.getAppId());
            String account = !Strings.empty(user.getAccount()) ? user.getAccount() : (!Strings.empty(user.getCellphone()) ? user.getCellphone() : user.getEmail());
            LeoAppUser leoAppUser = new LeoAppUser(user.getId(), account, user.getName());
            AccessToken accessToken = AccessTokenManager.get().online(leoAppUser, app, request.getRemoteAddr());
            List<String> lPC = new ArrayList<>();

            openPlatformUserReq.getValues().forEach((k, v) -> lPC.add(k));
            ThreadPools.execute(() -> userService.createSignLog(UserSignLog.Type.SIGN_IN, UserSignLog.Mode.PLATFORM, accessToken.getAppId(), accessToken.getUserId(), Strings.implode(lPC, ","), null));
            Logger.info("create access token '%s' for app '%s'", accessToken.getToken(), accessToken.getAppId());
            return new AppUserResp(leoAppUser.getId(), leoAppUser.getAccount(), leoAppUser.getName(), leoAppUser.getToken());
        }
        throw new LeoException(Resp.RespCode.DENIED);
    }

    /**
     * 查询应用
     *
     * @param token 令牌
     * @param appId 应用编号
     * @return 应用列表
     */
    @GetMapping("/apps/{token}/{appId}")
    public ListResp<AppResp> findApps(@PathVariable("token") String token, @PathVariable("appId") String appId) {
        AccessToken accessToken = AccessTokenManager.get().get(token, appId);
        ListResp<AppResp> lrAppResp = new ListResp<>();

        securityService.filter(appService.getAll(), accessToken.getUserId()).forEach(app -> {
            AppResp appResp = new AppResp();
            appResp.setId(app.getId());
            appResp.setName(app.getName());
            appResp.setCode(app.getCode());
            appResp.setLogo(app.getLogo());
            appResp.setColor(app.getColor());
            lrAppResp.add(appResp);
        });
        return lrAppResp;
    }

    /**
     * 查询导航
     *
     * @param type 类型
     * @param parentId 上级导航
     * @param token 令牌
     * @param appId 应用编号
     * @return 导航列表
     */
    @GetMapping("/navs/{type}/{parentId}/{token}/{appId}")
    public ListResp<NavResp> findNavs(@PathVariable("type") String type, @PathVariable("parentId") String parentId, @PathVariable("token") String token, @PathVariable("appId") String appId) {
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
            navResp.setUrl(nav.getPath().startsWith(NAV_PREFIX) ? nav.getPath().replace(NAV_PREFIX, app.getUrl()) : nav.getPath());
            navResp.setTarget(nav.getTarget());
            lrNavResp.add(navResp);
        });
        return lrNavResp;
    }

    /**
     * 检查应用是否合法
     *
     * @param appId 应用编号
     * @param timestamp 时间戳
     * @param sign 签名
     * @return 响应
     */
    @GetMapping("/check_app")
    public Resp checkApp(String appId, String timestamp, String sign) {
        App app = appService.getById(appId);
        return (app != null && APIs.validSign(app.getSecret(), sign, "appId", appId, "timestamp", timestamp)) ? new Resp(Resp.RespCode.SUCCESS) : new Resp(Resp.RespCode.DENIED);
    }
}

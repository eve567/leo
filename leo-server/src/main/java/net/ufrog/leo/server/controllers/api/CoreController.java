package net.ufrog.leo.server.controllers.api;

import net.ufrog.common.Link;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.client.LeoAppUser;
import net.ufrog.leo.client.api.beans.AppUserResp;
import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.domain.models.Nav;
import net.ufrog.leo.server.AccessToken;
import net.ufrog.leo.server.AccessTokenManager;
import net.ufrog.leo.service.AppService;
import net.ufrog.leo.service.NavService;
import net.ufrog.leo.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public CoreController(AppService appService, NavService navService, SecurityService securityService) {
        this.appService = appService;
        this.navService = navService;
        this.securityService = securityService;
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
     * 查询应用
     *
     * @param token 令牌
     * @param appId 应用编号
     * @return 应用列表
     */
    @GetMapping("/apps/{token}/{appId}")
    public List<App> findApps(@PathVariable("token") String token, @PathVariable("appId") String appId) {
        AccessToken accessToken = AccessTokenManager.get().get(token, appId);
        return securityService.filter(appService.findAll(), accessToken.getUserId());
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
    public List<Nav> findNavs(@PathVariable("type") String type, @PathVariable("parentId") String parentId, @PathVariable("token") String token, @PathVariable("appId") String appId) {
        AccessToken accessToken = AccessTokenManager.get().get(token, appId);
        List<Nav> lNav = Strings.equals("root", parentId) ? navService.findRoot(type, accessToken.getAppId()) : navService.findByParentId(parentId);
        App app = appService.findById(appId);

        lNav.stream().filter(nav -> nav.getPath().startsWith("@")).forEach(nav -> nav.setPath(nav.getPath().replace("@", app.getUrl())));
        return securityService.filter(Link.sort(lNav), accessToken.getUserId());
    }
}

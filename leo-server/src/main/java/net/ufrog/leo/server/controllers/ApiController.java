package net.ufrog.leo.server.controllers;

import net.ufrog.common.Link;
import net.ufrog.common.utils.Objects;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.domain.models.Nav;
import net.ufrog.leo.server.AccessToken;
import net.ufrog.leo.server.AccessTokenManager;
import net.ufrog.leo.service.AppService;
import net.ufrog.leo.service.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 接口控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-26
 * @since 0.1
 */
@RestController
public class ApiController {

    /** 应用业务接口 */
    private AppService appService;

    /** 导航业务接口 */
    private NavService navService;

    /**
     * 构造函数
     *
     * @param appService 应用业务接口
     * @param navService 导航业务接口
     */
    @Autowired
    public ApiController(AppService appService, NavService navService) {
        this.appService = appService;
        this.navService = navService;
    }

    /**
     * 查询应用
     *
     * @param token 令牌
     * @return 应用列表
     */
    @GetMapping("/apps/{token}")
    public List<App> findApps(@PathVariable("token") String token) {
        AccessToken accessToken = AccessTokenManager.get().get(token);
        return appService.findAll(); //TODO 过滤权限
    }

    /**
     * 查询导航
     *
     * @param token 令牌
     * @param type 类型
     * @param parentId 上级导航
     * @return 导航列表
     */
    @GetMapping("/navs/{token}/{type}/{parentId}")
    public List<Nav> findNavs(@PathVariable("token") String token, @PathVariable("type") String type, @PathVariable("parentId") String parentId) {
        AccessToken accessToken = AccessTokenManager.get().get(token);
        List<Nav> lNav = Strings.equals("root", parentId) ? navService.findRoot(type, accessToken.getAppId()) : navService.findByParentId(parentId);

        lNav.stream().filter(nav -> nav.getPath().startsWith("@")).forEach(nav -> nav.setPath(nav.getPath().replace("@", net.ufrog.common.app.App.current().toString())));
        return Link.sort(lNav); //TODO 权限过滤
    }
}

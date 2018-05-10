package net.ufrog.leo.server.controllers;

import net.ufrog.common.Logger;
import net.ufrog.common.Result;
import net.ufrog.common.ThreadPools;
import net.ufrog.common.app.App;
import net.ufrog.common.app.AppUser;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Passwords;
import net.ufrog.common.utils.Strings;
import net.ufrog.common.web.app.WebApp;
import net.ufrog.leo.client.app.LeoAppFilter;
import net.ufrog.leo.domain.models.User;
import net.ufrog.leo.domain.models.UserSignLog;
import net.ufrog.leo.server.accesstoken.AccessToken;
import net.ufrog.leo.server.accesstoken.AccessTokenManager;
import net.ufrog.leo.service.AppService;
import net.ufrog.leo.service.SecurityService;
import net.ufrog.leo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 索引控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-11
 * @since 3.0.0
 */
@Controller
public class IndexController {

    private static final String SESSION_ACCESS_TOKENS   = "access_tokens";

    /** 应用业务接口 */
    private AppService appService;

    /** 权限业务接口 */
    private SecurityService securityService;

    /** 用户业务接口 */
    private UserService userService;

    /**
     * 构造函数
     *
     * @param appService 应用业务接口
     * @param securityService 权限业务接口
     * @param userService 用户业务接口
     */
    @Autowired
    public IndexController(AppService appService, SecurityService securityService, UserService userService) {
        this.appService = appService;
        this.securityService = securityService;
        this.userService = userService;
    }

    /**
     * 索引
     *
     * @return view for index.html
     */
    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    /**
     * 索引
     *
     * @param mode 模式
     * @param model 模型
     * @return view for index.html
     */
    @GetMapping({"/{mode}", "/index/{mode}"})
    public String index(@PathVariable("mode") String mode, Model model) {
        model.addAttribute("mode", mode);
        return index();
    }

    /**
     * 登录表单
     *
     * @return view for sign_in.html
     */
    @GetMapping("/sign_in")
    public String signIn() {
        return "sign_in";
    }

    /**
     * 注销
     *
     * @return view for sign_in.html
     */
    @GetMapping("/sign_out")
    public String signOut() {
        AppUser appUser = App.current().getUser();
        if (appUser != null) {
            WebApp app = App.current(WebApp.class);

            //noinspection unchecked
            app.getSession(SESSION_ACCESS_TOKENS, List.class).map(list -> {
                //noinspection unchecked
                list.forEach(data -> {
                    AccessToken accessToken = (AccessToken) data;
                    AccessTokenManager.get().offline(appUser.getId(), accessToken.getAppId(), accessToken.getToken());
                    ThreadPools.execute(() -> userService.createSignLog(UserSignLog.Type.SIGN_OUT, UserSignLog.Mode.GATEWAY, accessToken.getAppId(), accessToken.getUserId(), null, null));
                });
                return list;
            }).orElseGet(() -> {
                List<AccessToken> lAccessToken = new ArrayList<>();
                app.setUser(null);
                app.setSession(SESSION_ACCESS_TOKENS, lAccessToken);
                return lAccessToken;
            });
        }
        return signIn();
    }

    /**
     * 用户认证
     *
     * @param account 账号
     * @param password 密码
     * @return 用户认证结果
     */
    @PostMapping("/authenticate")
    @ResponseBody
    public Result<AppUser> authenticate(String account, String password) {
        try {
            User user = userService.authenticate(account, password, UserService.UserType.STAFF, UserService.UserType.ROOT);
            AppUser appUser = new AppUser(user.getId(), account, user.getName());

            App.current().setUser(appUser);
            securityService.clearResourceMapping(appUser.getId());
            return Result.success(appUser, App.message("authenticate.success"));
        } catch (ServiceException e) {
            return Result.failure(App.message(e.getKey()));
        }
    }

    /**
     * 跳转
     *
     * @param appId 应用编号
     * @param request 请求
     * @return 跳转地址
     */
    @GetMapping("/redirect/{appId}")
    public String redirect(@PathVariable("appId") String appId, HttpServletRequest request) {
        net.ufrog.leo.domain.models.App app = appService.getById(appId);
        WebApp webApp = App.current(WebApp.class);
        @SuppressWarnings("unchecked") List<AccessToken> lAccessToken = webApp.getSession(SESSION_ACCESS_TOKENS, List.class).orElse(new ArrayList());

        AccessToken accessToken = lAccessToken.stream().filter(at -> Strings.equals(appId, at.getAppId())).findFirst().orElseGet(() -> {
            AccessToken at = AccessTokenManager.get().online(App.user(), app, request.getRemoteAddr());
            lAccessToken.add(at);
            webApp.setSession(SESSION_ACCESS_TOKENS, lAccessToken);
            ThreadPools.execute(() -> userService.createSignLog(UserSignLog.Type.SIGN_IN, UserSignLog.Mode.GATEWAY, at.getAppId(), at.getUserId(), null, null));
            Logger.debug("create access token '%s' for app '%s'", at.getToken(), at.getAppId());
            return at;
        });

        String uri = app.getUrl() + (app.getUrl().contains("?") ? "&" : "?") + LeoAppFilter.PARAM_KEY + "=" + accessToken.getToken();
        Logger.debug("redirect to uri: %s", uri);
        return "redirect:" + uri;
    }

    /**
     * 查询应用
     *
     * @return 应用列表
     */
    @GetMapping("/app_list")
    @ResponseBody
    public List<net.ufrog.leo.domain.models.App> findApps() {
        return securityService.filter(appService.findAll(), App.user().getId());
    }

    /**
     * 检查是否需要强制修改密码
     *
     * @return 检查结果
     */
    @GetMapping("/check_forced")
    @ResponseBody
    public Result<User> checkForced() {
        User user = userService.findById(App.user().getId());
        if (Strings.equals(User.Forced.TRUE, user.getForced())) {
            return Result.warning(user, App.message("reset.password.forced"));
        }
        return Result.success(user);
    }

    /**
     * 更新用户密码
     *
     * @param prev 原密码
     * @param next 新密码
     * @param confirm 确认密码
     * @return 更新结果
     */
    @RequestMapping(value = {"/update_password", "/reset_password"}, method = {RequestMethod.PATCH, RequestMethod.POST})
    @ResponseBody
    public Result<User> updatePassword(String prev, String next, String confirm) {
        if (Strings.equals(next, confirm)) {
            User user = userService.findById(App.user().getId());
            if (Passwords.match(prev, user.getPassword())) {
                user.setPassword(Passwords.encode(next));
                user.setForced(User.Forced.FALSE);
                userService.update(user);
                signOut();
                return Result.success(user, App.message("reset.password.success"));
            }
            return Result.failure(App.message("reset.password.failure.prev"));
        }
        return Result.failure(App.message("reset.password.failure.confirm"));
    }

    /**
     * 简单渲染视图
     *
     * @param view 视图
     * @return view for {view}.html
     */
    @GetMapping({"/view/{view}", "/render/{view}"})
    public String renderView(@PathVariable("view") String view) {
        return view.replaceAll("@", "/");
    }
}

package net.ufrog.leo.gateway.controllers;

import net.ufrog.aries.common.contract.ListResponse;
import net.ufrog.aries.common.contract.Response;
import net.ufrog.common.Result;
import net.ufrog.common.app.App;
import net.ufrog.common.app.AppUser;
import net.ufrog.common.utils.Strings;
import net.ufrog.common.web.app.WebApp;
import net.ufrog.leo.client.GatewayClient;
import net.ufrog.leo.client.contracts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 索引控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-05-15
 * @since 3.0.0
 */
@Controller
public class IndexController {

    private static final String SESSION_ACCESS_TOKENS   = "access_tokens";

    /**  */
    private final GatewayClient gatewayClient;

    /**
     * 构造函数
     *
     * @param gatewayClient gateway client
     */
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public IndexController(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    /**
     * 索引
     *
     * @return view for index.html
     */
    @GetMapping({"", "/", "/index"})
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
     * 用户认证
     *
     * @param account 账号
     * @param password 密码
     * @return 用户认证结果
     */
    @PostMapping("/authenticate")
    @ResponseBody
    public Result<?> authenticate(String account, String password) {
        AuthenticateRequest authenticateRequest = new AuthenticateRequest();
        authenticateRequest.setAccount(account);
        authenticateRequest.setPassword(password);
        authenticateRequest.setAppId(App.config("leo.appId"));
        authenticateRequest.setType(AuthenticateRequest.Type.CONSOLE);

        AppUserResponse appUserResponse = gatewayClient.authenticate(authenticateRequest);
        if (appUserResponse.isSuccess()) {
            AppUser appUser = new AppUser(appUserResponse.getId(), appUserResponse.getAccount(), appUserResponse.getName());
            App.current().setUser(appUser);
            return Result.success(appUser, App.message("authenticate.success"));
        } else {
            return Result.failure(appUserResponse.getMessage());
        }
    }

    /**
     * 检查是否需要强制修改密码
     *
     * @return 检查结果
     */
    @GetMapping("/check_forced")
    @ResponseBody
    public Result<?> checkForced() {
        Response response = gatewayClient.checkForced(App.user().getId());
        if (response.isSuccess()) {
            return Result.success("");
        } else {
            return Result.warning(App.message("reset.password.forced"));
        }
    }

    /**
     * 更新用户密码
     *
     * @param prev 原密码
     * @param next 新密码
     * @param confirm 确认密码
     * @return 更新结果
     */
    @RequestMapping(value = "/update_password", method = RequestMethod.POST)
    @ResponseBody
    public Result<?> updatePassword(String prev, String next, String confirm) {
        if (!Strings.empty(next) && Strings.equals(next, confirm)) {
            UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
            updatePasswordRequest.setUserId(App.user().getId());
            updatePasswordRequest.setPrev(prev);
            updatePasswordRequest.setNext(next);
            Response response = gatewayClient.updatePassword(updatePasswordRequest);

            signOut();
            if (response.isSuccess()) {
                return Result.success(App.message("reset.password.success"));
            } else {
                return Result.failure(response.getMessage());
            }
        }
        return Result.failure(App.message("reset.password.failure.confirm"));
    }

    /**
     * 查询应用
     *
     * @return 应用列表
     */
    @GetMapping("/app_list")
    @ResponseBody
    public List<AppResponse> findApps() {
        ListResponse<AppResponse> lrAppResponse = gatewayClient.findApps(App.user().getId());
        return lrAppResponse.getContent();
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
        WebApp app = App.current(WebApp.class);
        @SuppressWarnings("unchecked") List<RedirectResponse> lRedirectResponse = app.getSession(SESSION_ACCESS_TOKENS, List.class).orElse(new ArrayList());

        return "redirect:" + lRedirectResponse.stream().filter(redirectResponse -> Strings.equals(appId, redirectResponse.getAppId())).findFirst().orElseGet(() -> {
            AppUser appUser = app.getUser();
            AccessTokenRequest accessTokenRequest = new AccessTokenRequest();

            accessTokenRequest.setAppId(appId);
            accessTokenRequest.setUserId(appUser.getId());
            accessTokenRequest.setUserAccount(appUser.getAccount());
            accessTokenRequest.setUserName(appUser.getName());
            accessTokenRequest.setRemoteAddress(request.getRemoteAddr());
            RedirectResponse redirectResponse = gatewayClient.getRedirectUrl(accessTokenRequest);

            lRedirectResponse.add(redirectResponse);
            app.setSession(SESSION_ACCESS_TOKENS, lRedirectResponse);
            return redirectResponse;
        }).getUrl();
    }

    /**
     * 注销
     *
     * @return view for sign_in.html
     */
    @GetMapping("/sign_out")
    public String signOut() {
        Optional.ofNullable(App.current().getUser()).ifPresent(appUser -> {
            WebApp app = App.current(WebApp.class);
            //noinspection unchecked
            app.getSession(SESSION_ACCESS_TOKENS, List.class).ifPresent(list -> list.forEach(data -> {
                AccessTokenResponse accessTokenResponse = AccessTokenResponse.class.cast(data);
                SignOutRequest signOutRequest = new SignOutRequest();

                signOutRequest.setAppId(accessTokenResponse.getAppId());
                signOutRequest.setUserId(accessTokenResponse.getUserId());
                signOutRequest.setToken(accessTokenResponse.getToken());
                gatewayClient.signOut(signOutRequest);
            }));
            app.setUser(null);
            app.setSession(SESSION_ACCESS_TOKENS, new ArrayList<>());
        });
        return signIn();
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

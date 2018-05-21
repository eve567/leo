package net.ufrog.leo.server.controllers;

import net.ufrog.aries.common.contract.ListResponse;
import net.ufrog.aries.common.contract.Response;
import net.ufrog.common.Logger;
import net.ufrog.common.ThreadPools;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Passwords;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.client.GatewayClient;
import net.ufrog.leo.client.app.LeoAppFilter;
import net.ufrog.leo.client.app.LeoAppUser;
import net.ufrog.leo.client.contracts.*;
import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.domain.models.User;
import net.ufrog.leo.domain.models.UserSignLog;
import net.ufrog.leo.server.LeoContracts;
import net.ufrog.leo.server.accesstoken.AccessToken;
import net.ufrog.leo.server.accesstoken.AccessTokenManager;
import net.ufrog.leo.service.AppService;
import net.ufrog.leo.service.SecurityService;
import net.ufrog.leo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 4.0.1, 2018-05-16
 * @since 4.0.1
 */
@RestController
public class GatewayController implements GatewayClient {

    /** 应用业务接口 */
    private final AppService appService;

    /** 权限业务接口 */
    private final SecurityService securityService;

    /** 用户业务接口 */
    private final UserService userService;

    /**
     * 构造函数
     *
     * @param appService 应用业务接口
     * @param securityService 权限业务接口
     * @param userService 用户业务接口
     */
    @Autowired
    public GatewayController(AppService appService, SecurityService securityService, UserService userService) {
        this.appService = appService;
        this.securityService = securityService;
        this.userService = userService;
    }

    @Override
    public AppUserResponse authenticate(@RequestBody AuthenticateRequest authenticateRequest) {
        try {
            return Optional.ofNullable(userService.authenticate(authenticateRequest.getAccount(), authenticateRequest.getPassword(), UserService.UserType.ROOT, UserService.UserType.STAFF)).map(user -> {
                AppUserResponse appUserResponse = new AppUserResponse();

                appUserResponse.setId(user.getId());
                appUserResponse.setAccount(authenticateRequest.getAccount());
                appUserResponse.setName(user.getName());
                securityService.clearResourceMapping(user.getId());
                ThreadPools.execute(() -> userService.createSignLog(UserSignLog.Type.SIGN_IN, UserSignLog.Mode.GATEWAY, authenticateRequest.getAppId(), user.getId(), null, null));
                return appUserResponse;
            }).orElseGet(() -> Response.createResponse(ResultCode.AUTHENTICATE_FAIL, AppUserResponse.class));
        } catch (ServiceException e) {
            Logger.error(e.getMessage());
            AppUserResponse appUserResponse = Response.createResponse(ResultCode.AUTHENTICATE_FAIL, AppUserResponse.class);
            appUserResponse.setMessage(e.getLocalizedMessage());
            return appUserResponse;
        }
    }

    @Override
    public Response signOut(@RequestBody SignOutRequest signOutRequest) {
        AccessTokenManager.get().offline(signOutRequest.getUserId(), signOutRequest.getAppId(), signOutRequest.getToken());
        ThreadPools.execute(() -> userService.createSignLog(UserSignLog.Type.SIGN_OUT, UserSignLog.Mode.GATEWAY, signOutRequest.getAppId(), signOutRequest.getUserId(), null, null));
        return Response.createResponse(ResultCode.SUCCESS, Response.class);
    }

    @Override
    public RedirectResponse getRedirectUrl(@RequestBody AccessTokenRequest accessTokenRequest) {
        App app = appService.getById(accessTokenRequest.getAppId());
        LeoAppUser leoAppUser = new LeoAppUser(accessTokenRequest.getUserId(), accessTokenRequest.getUserAccount(), accessTokenRequest.getUserName());
        AccessToken accessToken = AccessTokenManager.get().online(leoAppUser, app, accessTokenRequest.getRemoteAddress());
        RedirectResponse redirectResponse = new RedirectResponse();

        redirectResponse.setAppId(accessToken.getAppId());
        redirectResponse.setUserId(accessToken.getUserId());
        redirectResponse.setToken(accessToken.getToken());
        redirectResponse.setExpires(accessToken.getExpires());
        redirectResponse.setUrl(app.getUrl() + (app.getUrl().contains("?") ? "&" : "?") + LeoAppFilter.PARAM_KEY + "=" + accessToken.getToken());
        ThreadPools.execute(() -> userService.createSignLog(UserSignLog.Type.SIGN_IN, UserSignLog.Mode.GATEWAY, accessToken.getAppId(), accessToken.getUserId(), null, null));
        return redirectResponse;
    }

    @Override
    @SuppressWarnings("MVCPathVariableInspection")
    public ListResponse<AppResponse> findApps(@PathVariable("userId") String userId) {
        List<App> lApp = securityService.filter(appService.findAll(), userId);
        return new ListResponse<>(lApp.stream().map(AppController::toAppResponse).collect(Collectors.toList()));
    }

    @Override
    @SuppressWarnings("MVCPathVariableInspection")
    public Response checkForced(@PathVariable("userId") String userId) {
        User user = userService.findById(userId);
        if (Strings.equals(User.Forced.TRUE, user.getForced())) {
            return Response.createResponse(ResultCode.UNKNOW, Response.class);
        }
        return Response.createResponse(ResultCode.SUCCESS, Response.class);
    }

    @Override
    public Response updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        User user = userService.findById(updatePasswordRequest.getUserId());
        if (Passwords.match(updatePasswordRequest.getPrev(), user.getPassword())) {
            user.setPassword(Passwords.encode(updatePasswordRequest.getNext()));
            user.setForced(User.Forced.FALSE);
            userService.update(user);
            return Response.createResponse(ResultCode.SUCCESS, Response.class);
        }
        Response response = Response.createResponse(ResultCode.USER_UPDATE_FAIL, Response.class);
        response.setMessage(net.ufrog.common.app.App.message("reset.password.failure.prev"));
        return response;
    }
}

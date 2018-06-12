package net.ufrog.leo.server.controllers;

import net.ufrog.aries.common.contract.PageResponse;
import net.ufrog.aries.common.contract.Response;
import net.ufrog.aries.common.jpa.ID;
import net.ufrog.common.Logger;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Codecs;
import net.ufrog.common.utils.Passwords;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.client.UserClient;
import net.ufrog.leo.client.contracts.OpenPlatformRequest;
import net.ufrog.leo.client.contracts.ResultCode;
import net.ufrog.leo.client.contracts.UserRequest;
import net.ufrog.leo.client.contracts.UserResponse;
import net.ufrog.leo.domain.models.User;
import net.ufrog.leo.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * 用户控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-11
 * @since 3.0.0
 */
@RestController
@CrossOrigin
public class UserController implements UserClient {

    /** 用户业务接口 */
    private UserService userService;

    /**
     * 构造函数
     *
     * @param userService 用户业务接口
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserResponse create(@RequestBody UserRequest userRequest) {
        User user = toUserModel(userRequest);
        if (Strings.empty(user.getPassword())) {
            String password = Strings.random(8);
            user.setPassword(password);
            user.setForced(User.Forced.TRUE);
            Logger.info("generate user password and forced update password.");
        }
        user.setStatus(User.Status.ENABLED);
        user.setPassword(Passwords.encode(user.getPassword()));
        return toUserResponse(userService.create(user));
    }

    @Override
    public UserResponse read(String id) {
        return null;
    }

    @Override
    public PageResponse<UserResponse> read(UserRequest userRequest) {
        return null;
    }

    @Override
    public UserResponse update(String id, UserRequest userRequest) {
        return null;
    }

    @Override
    public UserResponse updatePassword(String id, UserRequest userRequest) {
        return null;
    }

    @Override
    public UserResponse freezeOrUnfreeze(String id) {
        return null;
    }

    @Override
    public UserResponse delete(String id) {
        return null;
    }

    @Override
    public UserResponse readOrCreateByOpenPlatform(@RequestBody OpenPlatformRequest openPlatformRequest) {
        return userService.findByOpenPlatform(openPlatformRequest.getCodeValuePairs()).map(UserController::toUserResponse).orElseGet(() -> {
            User user = new User();
            user.setAccount(Strings.empty(openPlatformRequest.getAccount(), Codecs.uuid()));
            user.setCellphone(Strings.empty(openPlatformRequest.getCellphone(), ID.NULL));
            user.setEmail(Strings.empty(openPlatformRequest.getEmail(), ID.NULL));
            user.setName(Strings.empty(openPlatformRequest.getName(), ID.NULL));
            user.setPassword(Passwords.encode(Strings.random(8)));
            user.setType(UserRequest.Type.CLIENT);
            user.setForced(User.Forced.TRUE);
            user.setStatus(User.Status.ENABLED);

            userService.create(user);
            userService.registerOpenPlatform(openPlatformRequest.getCodeValuePairs(), user.getId());
            return toUserResponse(user);
        });
    }

    @Override
    public Response registerOpenPlatform(@RequestBody OpenPlatformRequest openPlatformRequest) {
        return userService.findByOpenPlatform(openPlatformRequest.getCodeValuePairs()).map(user -> {
            if (Strings.equals(user.getId(), openPlatformRequest.getUserId())) {
                return Response.createResponse(ResultCode.SUCCESS, Response.class);
            } else {
                String msg = openPlatformRequest.getCodeValuePairs().entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining(", "));
                throw new ServiceException("another user '" + user.getId() + "' has same open platform info: " + msg);
            }
        }).orElseGet(() -> {
            userService.registerOpenPlatform(openPlatformRequest.getCodeValuePairs(), openPlatformRequest.getUserId());
            return Response.createResponse(ResultCode.SUCCESS, Response.class);
        });
    }

    /**
     * 转换用户模型
     *
     * @param userRequest 用户请求
     * @return 用户模型
     */
    private static User toUserModel(UserRequest userRequest) {
        User user = new User();
        user.setAccount(userRequest.getAccount());
        user.setCellphone(userRequest.getCellphone());
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());
        user.setType(userRequest.getType());
        user.setStatus(User.Status.PENDING);
        user.setForced(User.Forced.FALSE);
        return user;
    }

    /**
     * 转换用户响应
     *
     * @param user 用户模型
     * @return 用户响应
     */
    private static UserResponse toUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setResultCode(ResultCode.SUCCESS);
        userResponse.setId(user.getId());
        userResponse.setAccount(user.getAccount());
        userResponse.setCellphone(user.getCellphone());
        userResponse.setEmail(user.getEmail());
        userResponse.setName(user.getName());
        userResponse.setStatus(user.getStatus());
        userResponse.setStatusName(user.getStatusName());
        userResponse.setType(user.getType());
        userResponse.setTypeName(user.getTypeName());
        return userResponse;
    }
}

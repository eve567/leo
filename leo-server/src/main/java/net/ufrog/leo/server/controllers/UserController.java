package net.ufrog.leo.server.controllers;

import net.ufrog.aries.common.contract.PageResponse;
import net.ufrog.common.Logger;
import net.ufrog.common.utils.Passwords;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.client.UserClient;
import net.ufrog.leo.client.contract.UserRequest;
import net.ufrog.leo.client.contract.UserResponse;
import net.ufrog.leo.domain.models.User;
import net.ufrog.leo.server.LeoContracts;
import net.ufrog.leo.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public UserResponse create(@RequestBody UserRequest userReq) {
        User user = LeoContracts.toUserModel(userReq);
        if (Strings.empty(user.getPassword())) {
            String password = Strings.random(8);
            user.setPassword(password);
            user.setForced(User.Forced.TRUE);
            Logger.info("generate user password and forced update password.");
        }
        user.setStatus(User.Status.ENABLED);
        user.setPassword(Passwords.encode(user.getPassword()));
        return LeoContracts.toUserResp(userService.create(user));
    }

    @Override
    public UserResponse read(String id) {
        return null;
    }

    @Override
    public PageResponse<UserResponse> read(UserRequest userReq) {
        return null;
    }

    @Override
    public UserResponse update(String id, UserRequest userReq) {
        return null;
    }

    @Override
    public UserResponse updatePassword(String id, UserRequest userReq) {
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
}

package net.ufrog.leo.server.controllers;

import net.ufrog.aries.common.contract.PageResp;
import net.ufrog.leo.client.UserClient;
import net.ufrog.leo.client.contract.UserReq;
import net.ufrog.leo.client.contract.UserResp;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    @Override
    public UserResp create(UserReq userReq) {
        return null;
    }

    @Override
    public UserResp read(String id) {
        return null;
    }

    @Override
    public PageResp<UserResp> read(UserReq userReq) {
        return null;
    }

    @Override
    public UserResp update(String id, UserReq userReq) {
        return null;
    }

    @Override
    public UserResp updatePassword(String id, UserReq userReq) {
        return null;
    }

    @Override
    public UserResp freezeOrUnfreeze(String id) {
        return null;
    }

    @Override
    public UserResp delete(String id) {
        return null;
    }
}

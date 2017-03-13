package net.ufrog.leo.server.controllers;

import net.ufrog.common.Result;
import net.ufrog.common.app.App;
import net.ufrog.common.app.AppUser;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.leo.domain.mappers.UserMapper;
import net.ufrog.leo.domain.models.User;
import net.ufrog.leo.server.beans.access.token.AccessToken;
import net.ufrog.leo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 索引控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-02-26
 * @since 0.1
 */
@Controller
public class IndexController {

    /**  */
    private UserMapper userMapper;

    /** 用户业务接口 */
    private UserService userService;

    /**
     * 构造函数
     *
     * @param userMapper user mapper
     * @param userService 用户业务接口
     */
    @Autowired
    public IndexController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    /**
     * 索引
     *
     * @return view for index
     */
    @GetMapping({"", "/", "/index"})
    public String index() {
        User user = userMapper.findOne("id");
        if (user != null) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>");
            System.out.println(">>>>>>>>>>" + user.getId());
            System.out.println(">>>>>>>>>>" + user.getName());
            System.out.println(">>>>>>>>>>>>>>>>>>>>");
        }
        return "index";
    }

    /**
     * 登录表单
     *
     * @return view for sign_in
     */
    @GetMapping("/sign_in")
    public String signIn() {
        return "sign_in";
    }

    /**
     * 注销
     *
     * @return view for sign_in
     */
    @GetMapping("/sign_out")
    public String signOut() {
        AppUser appUser = App.current().getUser();
        if (appUser != null) {
            //TODO 插入移除令牌
            App.current().setUser(null);
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
            AppUser appUser = new AppUser(user.getId(), user.getAccount(), user.getName());

            AccessToken.newAccessToken(appUser);
            App.current().setUser(appUser);
            return Result.success(appUser, App.message(""));
        } catch (ServiceException e) {
            return Result.failure(App.message(e.getKey()));
        }
    }
}

package net.ufrog.leo.console.controllers;

import net.ufrog.common.Mailer;
import net.ufrog.common.Result;
import net.ufrog.common.app.App;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Passwords;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.models.User;
import net.ufrog.leo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 团队控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-25
 * @since 0.1
 */
@Controller
@RequestMapping("/team")
public class TeamController {

    /** 用户业务接口 */
    private final UserService userService;

    /**
     * 构造函数
     *
     * @param userService 用户业务接口
     */
    @Autowired
    public TeamController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 索引
     *
     * @return view for team/index
     */
    @GetMapping({"", "/", "/index"})
    public String index() {
        return "team/index";
    }

    /**
     * 创建团队成员
     *
     * @param user 用户模型
     * @return 创建结果
     */
    @PostMapping("/create_user")
    @ResponseBody
    public Result<User> createUser(@RequestBody User user) {
        String password = Strings.random(8);

        user.setPassword(Passwords.encode(password));
        user.setForced(User.Forced.TRUE);
        user.setType(User.Type.STAFF);
        user.setStatus(User.Status.ENABLED);
        userService.create(user);

        Mailer.sendText("leo user password", password, user.getEmail());    //TODO template for mail content
        return Result.success(user, App.message("team.user.create.success", user.getName()));
    }

    /**
     * 重置团队成员密码
     *
     * @param userId 成员编号
     * @return 重置结果
     */
    @PutMapping("/reset/{userId}")
    @ResponseBody
    public Result<User> reset(@PathVariable("userId") String userId) {
        User user = userService.findById(userId);
        String password = Strings.random(8);

        user.setPassword(Passwords.encode(password));
        user.setForced(User.Forced.TRUE);
        userService.update(user);

        Mailer.sendText("leo user password", password, user.getEmail());    //TODO template for mail content
        return Result.success(user, App.message("team.user.reset.success", user.getName()));
    }

    /**
     * 冻结/解冻团队成员
     *
     * @param userId 成员编号
     * @return 冻结结果
     */
    @PutMapping("/freeze/{userId}")
    @ResponseBody
    public Result<User> freeze(@PathVariable("userId") String userId) {
        User user = userService.findById(userId);
        String key;

        if (Strings.equals(User.Status.ENABLED, user.getStatus())) {
            user.setStatus(User.Status.FROZEN);
            key = "team.user.freeze.success.freeze";
        } else if (Strings.equals(User.Status.FROZEN, user.getStatus())) {
            user.setStatus(User.Status.ENABLED);
            key = "team.user.freeze.success.unfreeze";
        } else {
            return Result.failure(App.message("team.freeze.failure.status", user.getName()));
        }

        userService.update(user);
        return Result.success(user, App.message(key, user.getName()));
    }
}

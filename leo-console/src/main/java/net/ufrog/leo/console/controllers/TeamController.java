package net.ufrog.leo.console.controllers;

import net.ufrog.common.Mailer;
import net.ufrog.common.Result;
import net.ufrog.common.app.App;
import net.ufrog.common.jetbrick.Templates;
import net.ufrog.common.utils.Codecs;
import net.ufrog.common.utils.Objects;
import net.ufrog.common.utils.Passwords;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.console.forms.UserRoleBindForm;
import net.ufrog.leo.domain.models.Role;
import net.ufrog.leo.domain.models.User;
import net.ufrog.leo.domain.models.UserRole;
import net.ufrog.leo.service.RoleService;
import net.ufrog.leo.service.UserService;
import net.ufrog.leo.service.beans.Props;
import net.ufrog.leo.service.storages.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /** 角色业务接口 */
    private final RoleService roleService;

    /** 用户业务接口 */
    private final UserService userService;

    /** 数据仓储 */
    private final Storage storage;

    /**
     * 构造函数
     *
     * @param roleService 角色业务接口
     * @param userService 用户业务接口
     * @param storage 数据仓储
     */
    @Autowired
    public TeamController(RoleService roleService, UserService userService, Storage storage) {
        this.roleService = roleService;
        this.userService = userService;
        this.storage = storage;
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

        Map<String, Object> args = Objects.map("password", password, "account", user.getEmail());
        String key = Props.getMailTplUserAdd();
        String tpl = new String(storage.get(key), Props.getAppCharset());
        String uuid = Codecs.uuid();
        Mailer.sendHtml(App.message("mail.subject.create.user"), Templates.render(uuid, tpl, args), user.getEmail());
        Templates.clear(uuid);
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

        Map<String, Object> args = Objects.map("password", password, "account", user.getEmail());
        String key = Props.getMailTplUserResetPassword();
        String tpl = new String(storage.get(key), Props.getAppCharset());
        String uuid = Codecs.uuid();
        Mailer.sendHtml(App.message("mail.subject.reset.password"), Templates.render(uuid, tpl, args), user.getEmail());
        Templates.clear(uuid);
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

    /**
     * 查询所有角色
     *
     * @param appId 应用编号
     * @return 角色列表
     */
    @GetMapping("/roles/{appId}")
    @ResponseBody
    public List<Role> findRoles(@PathVariable("appId") String appId) {
        return roleService.findByAppId(appId);
    }

    /**
     * 查询已绑定角色
     *
     * @param appId 应用编号
     * @param userId 用户编号
     * @return 已绑定角色编号列表
     */
    @GetMapping("/user_roles/{appId}/{userId}")
    @ResponseBody
    public List<String> findBindRoles(@PathVariable("appId") String appId, @PathVariable("userId") String userId) {
        List<UserRole> lUserRole = userService.findUserRoles(appId, userId);
        List<String> lRoleId = new ArrayList<>(lUserRole.size());

        lUserRole.forEach(userRole -> lRoleId.add(userRole.getRoleId()));
        return lRoleId;
    }

    /**
     * 绑定角色
     *
     * @param userRoleBindForm 用户角色绑定表单
     * @return 绑定结果
     */
    @PostMapping("/bind_roles")
    @ResponseBody
    public Result<List<UserRole>> bindRoles(@RequestBody UserRoleBindForm userRoleBindForm) {
        List<UserRole> lUserRole = userService.bindRoles(userRoleBindForm.getUserId(), userRoleBindForm.getAppId(), userRoleBindForm.getRoles());
        User user = userService.findById(userRoleBindForm.getUserId());
        return Result.success(lUserRole, App.message("team.user.bind.success", user.getName()));
    }
}

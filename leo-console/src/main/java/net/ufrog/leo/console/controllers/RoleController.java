package net.ufrog.leo.console.controllers;

import net.ufrog.common.Result;
import net.ufrog.common.app.App;
import net.ufrog.common.utils.Objects;
import net.ufrog.leo.domain.models.Role;
import net.ufrog.leo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 角色控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-09
 * @since 0.1
 */
@Controller
public class RoleController {

    /** 角色业务接口 */
    private RoleService roleService;

    /**
     * 构造函数
     *
     * @param roleService 角色业务接口
     */
    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 索引
     *
     * @return view for role/index
     */
    @GetMapping({"/role", "/role/", "/role/index"})
    public String index() {
        return "role/index";
    }

    /**
     * 查询所有角色
     *
     * @param appId 应用编号
     * @param page 当前页码
     * @param size 分页大小
     * @return 角色分页对象
     */
    @GetMapping("/roles/{appId}")
    @ResponseBody
    public Page<Role> findAll(@PathVariable("appId") String appId, Integer page, Integer size) {
        return roleService.findByAppId(appId, page, size);
    }

    /**
     * 创建角色
     *
     * @param role 角色对象
     * @return 创建结果
     */
    @PostMapping("/role")
    @ResponseBody
    public Result<Role> create(@RequestBody Role role) {
        Objects.trimStringFields(role, "id", "creator", "updater");
        return Result.success(roleService.create(role), App.message("role.create.success", role.getName()));
    }

    /**
     * 更新角色
     *
     * @param role 角色对象
     * @return 更新结果
     */
    @PutMapping("/role")
    @ResponseBody
    public Result<Role> update(@RequestBody Role role) {
        Objects.trimStringFields(role, "id", "creator", "updater");
        return Result.success(roleService.update(role), App.message("role.update.success", role.getName()));
    }
}

package net.ufrog.leo.console.controllers;

import net.ufrog.common.Result;
import net.ufrog.common.app.App;
import net.ufrog.common.dict.Dicts;
import net.ufrog.common.spring.app.SpringWebApp;
import net.ufrog.common.utils.Objects;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.console.forms.RoleResourceBindForm;
import net.ufrog.leo.console.loaders.ResourceLoader;
import net.ufrog.leo.domain.models.Resource;
import net.ufrog.leo.domain.models.Role;
import net.ufrog.leo.domain.models.RoleResource;
import net.ufrog.leo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-09
 * @since 0.1
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    /** 角色业务接口 */
    private final RoleService roleService;

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
    @GetMapping({"", "/", "/index"})
    public String index() {
        return "role/index";
    }

    /**
     * 资源视图
     *
     * @param type 类型
     * @return view for role/resource_${type}
     */
    @GetMapping("/resource/_{type}")
    public String resource(@PathVariable("type") String type) {
        return "role/resource_" + type;
    }

    /**
     * 查询所有角色
     *
     * @param appId 应用编号
     * @param page 当前页码
     * @param size 分页大小
     * @return 角色分页对象
     */
    @GetMapping("/find_all/{appId}")
    @ResponseBody
    public Page<Role> findAll(@PathVariable("appId") String appId, Integer page, Integer size) {
        return roleService.findByAppId(appId, page, size);
    }

    /**
     * 查询模型
     *
     * @param type 类型
     * @return 资源对象列表
     */
    @GetMapping("/resource_models/{type}")
    @ResponseBody
    public List<ResourceLoader.ResourceWrapper> findResourceModels(@PathVariable("type") String type) {
        return SpringWebApp.getBean("resource_" + type, ResourceLoader.class).load();
    }

    /**
     * 查询绑定资源
     *
     * @param roleId 角色编号
     * @param type 类型
     * @return 角色资源绑定表单
     */
    @GetMapping("/resources/{roleId}/{type}")
    @ResponseBody
    public RoleResourceBindForm findResources(@PathVariable("roleId") String roleId, @PathVariable("type") String type) {
        RoleResourceBindForm roleResourceBindForm = new RoleResourceBindForm();
        List<String> lAllow = new ArrayList<>();
        List<String> lBan = new ArrayList<>();

        roleService.findRoleResources(roleId, type).forEach(roleResource -> {
            if (Strings.equals(RoleResource.Type.ALLOW, roleResource.getType())) {
                lAllow.add(roleResource.getResourceId());
            } else if (Strings.equals(RoleResource.Type.BAN, roleResource.getType())) {
                lBan.add(roleResource.getResourceId());
            }
        });
        roleResourceBindForm.setAllows(lAllow.toArray(new String[0]));
        roleResourceBindForm.setBans(lBan.toArray(new String[0]));
        return roleResourceBindForm;
    }

    /**
     * 创建角色
     *
     * @param role 角色对象
     * @return 创建结果
     */
    @PostMapping("/create")
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
    @PutMapping("/update")
    @ResponseBody
    public Result<Role> update(@RequestBody Role role) {
        Objects.trimStringFields(role, "id", "creator", "updater");
        return Result.success(roleService.update(role), App.message("role.update.success", role.getName()));
    }

    /**
     * 删除角色
     *
     * @param id 角色编号
     * @return 删除角色
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result<Role> delete(@PathVariable("id") String id) {
        Role role = roleService.delete(id);
        return Result.success(role, App.message("role.delete.success", role.getName()));
    }

    /**
     * 绑定资源
     *
     * @param roleResourceBindForm 角色资源绑定表单
     * @return 绑定结果
     */
    @PostMapping("/bind_resources")
    @ResponseBody
    public Result<List<RoleResource>> bindResources(@RequestBody RoleResourceBindForm roleResourceBindForm) {
        List<RoleResource> lRoleResource = roleService.bindResources(roleResourceBindForm.getRoleId(), roleResourceBindForm.getType(), roleResourceBindForm.getAllows(), roleResourceBindForm.getBans());
        Role role = roleService.findById(roleResourceBindForm.getRoleId());
        return Result.success(lRoleResource, App.message("role.bind.success", role.getName(), Dicts.name(roleResourceBindForm.getType(), Resource.Type.class)));
    }
}

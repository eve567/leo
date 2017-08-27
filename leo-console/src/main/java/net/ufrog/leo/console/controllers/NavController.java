package net.ufrog.leo.console.controllers;

import net.ufrog.common.Result;
import net.ufrog.common.app.App;
import net.ufrog.common.utils.Objects;
import net.ufrog.leo.domain.models.Nav;
import net.ufrog.leo.service.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 导航控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-04-17
 * @since 0.1
 */
@Controller
@RequestMapping("/nav")
public class NavController {

    /** 导航业务接口 */
    private NavService navService;

    /**
     * 构造函数
     *
     * @param navService 导航业务接口
     */
    @Autowired
    public NavController(NavService navService) {
        this.navService = navService;
    }

    /**
     * 索引
     *
     * @return view for nav.index.html
     */
    @GetMapping({"", "/", "/index"})
    public String index() {
        return "nav/index";
    }

    /**
     * 查询导航列表
     *
     * @param type 类型
     * @param parentId 上级编号
     * @param appId 应用编号
     * @return 导航列表
     */
    @GetMapping("/find_children/{type}/{parentId}/{appId}")
    @ResponseBody
    public List<Nav> findChildren(@PathVariable("type") String type, @PathVariable("parentId") String parentId, @PathVariable("appId") String appId) {
        return navService.findChildren(type, appId, parentId);
    }

    /**
     * 创建导航
     *
     * @param nav 导航
     * @return 创建结果
     */
    @PostMapping("/create")
    @ResponseBody
    public Result<Nav> create(@RequestBody Nav nav) {
        Objects.trimStringFields(nav, "id", "creator", "updater");
        return Result.success(navService.create(nav), App.message("nav.create.success", nav.getName(), nav.getCode()));
    }

    /**
     * 更新导航
     *
     * @param nav 导航
     * @return 更新结果
     */
    @PutMapping("/update")
    @ResponseBody
    public Result<Nav> update(@RequestBody Nav nav) {
        Objects.trimStringFields(nav, "id", "creator", "updater");
        return Result.success(navService.update(nav), App.message("nav.update.success", nav.getName(), nav.getCode()));
    }

    /**
     * 删除导航
     *
     * @param id 编号
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result<Nav> delete(@PathVariable("id") String id) {
        Nav nav = navService.delete(id);
        return Result.success(nav, App.message("nav.delete.success", nav.getName(), nav.getCode()));
    }
}

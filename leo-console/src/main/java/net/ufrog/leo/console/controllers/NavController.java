package net.ufrog.leo.console.controllers;

import net.ufrog.common.Result;
import net.ufrog.common.app.App;
import net.ufrog.common.utils.Strings;
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
     * @return view for nav.index
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
    @GetMapping("/find/{type}/{parentId}/{appId}")
    @ResponseBody
    public List<Nav> find(@PathVariable("type") String type, @PathVariable("parentId") String parentId, @PathVariable("appId") String appId) {
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
        nav.setName(nav.getName().trim());
        nav.setCode(nav.getCode().trim());
        nav.setPath(nav.getPath().trim());
        nav.setTarget(nav.getTarget().trim());
        nav.setParentId(nav.getParentId().trim());
        if (!Strings.empty(nav.getSubname())) nav.setSubname(nav.getSubname().trim());
        return Result.success(navService.create(nav), App.message("nav.create.success", nav.getName()));
    }
}

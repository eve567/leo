package net.ufrog.leo.console.controllers;

import net.ufrog.common.Link;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.models.Nav;
import net.ufrog.leo.service.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    private static final String KEY_ROOT    = "root";

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
        return Strings.equals(KEY_ROOT, parentId) ? navService.findRoot(type, appId) : navService.findByParentId(parentId);
    }
}

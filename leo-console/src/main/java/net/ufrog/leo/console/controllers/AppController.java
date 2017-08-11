package net.ufrog.leo.console.controllers;

import net.ufrog.common.Result;
import net.ufrog.common.utils.Objects;
import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 应用控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-07
 * @since 0.1
 */
@Controller
public class AppController {

    /** 应用业务接口 */
    private AppService appService;

    /**
     * 构造函数
     *
     * @param appService 应用业务接口
     */
    @Autowired
    public AppController(AppService appService) {
        this.appService = appService;
    }

    /**
     * 索引
     *
     * @return view for app/index.html
     */
    @GetMapping({"/app", "/app/", "/app/index"})
    public String index() {
        return "app/index";
    }

    /**
     * 查询所有应用
     *
     * @param page 页码
     * @param size 分页大小
     * @return 应用分页对象
     */
    @GetMapping("/apps")
    @ResponseBody
    public Page<App> findAll(Integer page, Integer size) {
        return appService.findAll(page, size);
    }

    /**
     * 创建应用
     *
     * @param app 应用对象
     * @return 创建结果
     */
    @PostMapping("/app")
    @ResponseBody
    public Result<App> create(@RequestBody App app) {
        Objects.trimStringFields(app, "id", "creator", "updater");
        return Result.success(appService.create(app), net.ufrog.common.app.App.message("app.create.success", app.getName()));
    }

    /**
     * 更新应用
     *
     * @param app 应用对象
     * @return 更新结果
     */
    @PutMapping("/app")
    @ResponseBody
    public Result<App> update(@RequestBody App app) {
        Objects.trimStringFields(app, "id", "creator", "updater");
        return Result.success(appService.update(app), net.ufrog.common.app.App.message("app.update.success", app.getName()));
    }
}

package net.ufrog.leo.console.controllers;

import net.ufrog.common.Result;
import net.ufrog.common.dict.Dicts;
import net.ufrog.common.utils.Objects;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.console.forms.AppResourceTypeBindForm;
import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.domain.models.AppResource;
import net.ufrog.leo.domain.models.Resource;
import net.ufrog.leo.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 应用控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-07
 * @since 0.1
 */
@Controller
@RequestMapping("/app")
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
    @GetMapping({"", "/", "/index"})
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
    @GetMapping("/find_all_page")
    @ResponseBody
    public Page<App> findAll(Integer page, Integer size) {
        return appService.findAll(page, size);
    }

    /**
     * 查询所有应用
     *
     * @return
     */
    @GetMapping("/find_all")
    @ResponseBody
    public List<App> findAll() {
        return appService.findAll();
    }

    /**
     * 查询应用资源关系
     *
     * @param id 应用编号
     * @param type 类型
     * @return 应用资源关系列表
     */
    @GetMapping("/resource_types/{id}/{type}")
    @ResponseBody
    public List<AppResource> findResourceTypes(@PathVariable("id") String id, @PathVariable("type") String type) {
        List<AppResource> lAppResource = appService.findResourceTypes(id);
        if (Strings.equals("all", type)) {
            List<AppResource> lAR = new ArrayList<>();
            Map<Object, Dicts.Elem> mResourceType = Dicts.elements(Resource.Type.class);

            mResourceType.entrySet().stream().filter(entry -> {
                for (AppResource appResource: lAppResource) {
                    if (Strings.equals(appResource.getType(), String.class.cast(entry.getKey()))) return false;
                }
                return true;
            }).forEach(entry -> {
                AppResource appResource = new AppResource();
                appResource.setType(String.class.cast(entry.getKey()));
                lAR.add(appResource);
            });
            lAppResource.addAll(lAR);
        }
        return lAppResource;
    }

    /**
     * 创建应用
     *
     * @param app 应用对象
     * @return 创建结果
     */
    @PostMapping("/create")
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
    @PutMapping("/update")
    @ResponseBody
    public Result<App> update(@RequestBody App app) {
        Objects.trimStringFields(app, "id", "creator", "updater");
        return Result.success(appService.update(app), net.ufrog.common.app.App.message("app.update.success", app.getName()));
    }

    /**
     * 绑定资源类型
     *
     * @param appId 应用编号
     * @param appResourceTypeBindForm 应用资源类型绑定表单
     * @return 绑定结果
     */
    @PostMapping("/bind_resource_types/{appId}")
    @ResponseBody
    public Result<List<AppResource>> bind(@PathVariable("appId") String appId, @RequestBody AppResourceTypeBindForm appResourceTypeBindForm) {
        return Result.success(appService.bindResourceTypes(appId, appResourceTypeBindForm.getResourceTypes()), net.ufrog.common.app.App.message("app.bind.success"));
    }
}

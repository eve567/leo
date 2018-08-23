package net.ufrog.leo.console.controllers;

import net.ufrog.common.Result;
import net.ufrog.common.app.App;
import net.ufrog.common.utils.Objects;
import net.ufrog.leo.domain.models.View;
import net.ufrog.leo.domain.models.ViewItem;
import net.ufrog.leo.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 视图控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 5.0.0, 2018-08-13
 * @since 5.0.0
 */
@Controller
@RequestMapping(value = "/view")
public class ViewController {

    /** 视图业务接口 */
    private final ViewService viewService;

    /**
     * 构造函数
     *
     * @param viewService 视图业务接口
     */
    @Autowired
    public ViewController(ViewService viewService) {
        this.viewService = viewService;
    }

    /**
     * 索引
     *
     * @return view for view/index.html
     */
    @GetMapping({"", "/", "/index"})
    public String index() {
        return "view/index";
    }

    /**
     * 查询视图
     *
     * @param appId 应用编号
     * @param page 当前页码
     * @param size 分页大小
     * @return 分页视图对象
     */
    @GetMapping("/{appId}")
    @ResponseBody
    public Page<View> find(@PathVariable("appId") String appId, Integer page, Integer size) {
        return viewService.read(appId, page, size);
    }

    /**
     * 创建视图
     *
     * @param view 视图对象
     * @return 视图创建结果
     */
    @PostMapping("/create")
    @ResponseBody
    public Result<View> create(@RequestBody View view) {
        Objects.trimStringFields(view);
        return Result.success(viewService.create(view), App.message("view.create.success", view.getName()));
    }

    /**
     * 修改视图
     *
     * @param id 编号
     * @param view 视图对象
     * @return 视图修改结果
     */
    @PutMapping("/update/{id}")
    @ResponseBody
    public Result<View> update(@PathVariable("id") String id, @RequestBody View view) {
        Objects.trimStringFields(view);
        return Result.success(viewService.update(id, view), App.message("view.update.success", view.getName()));
    }

    /**
     * 删除视图
     *
     * @param id 编号
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result<View> delete(@PathVariable("id") String id) {
        View view = viewService.delete(id);
        return Result.success(view, App.message("view.delete.success", view.getName()));
    }

    /**
     * 查询视图元素
     *
     * @param viewId 视图编号
     * @return 视图元素列表
     */
    @GetMapping("/items/{viewId}")
    @ResponseBody
    public List<ViewItem> findItems(@PathVariable("viewId") String viewId) {
        return viewService.readItems(viewId);
    }

    /**
     * 创建视图元素
     *
     * @param viewItem 视图元素对象
     * @return 创建结果
     */
    @PostMapping("/item")
    @ResponseBody
    public Result<ViewItem> createItem(@RequestBody ViewItem viewItem) {
        Objects.trimStringFields(viewItem);
        return Result.success(viewService.createItem(viewItem), App.message("view.item.create.success", viewItem.getName()));
    }

    /**
     * 删除视图元素
     *
     * @param itemId 元素编号
     * @return 删除结果
     */
    @DeleteMapping("/item/{itemId}")
    @ResponseBody
    public Result<ViewItem> deleteItem(@PathVariable("itemId") String itemId) {
        ViewItem viewItem = viewService.deleteItem(itemId);
        return Result.success(viewItem, App.message("view.item.delete.failure", viewItem.getName()));
    }
}

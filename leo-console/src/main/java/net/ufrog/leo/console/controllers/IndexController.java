package net.ufrog.leo.console.controllers;

import com.alibaba.fastjson.JSON;
import net.ufrog.common.Logger;
import net.ufrog.common.Result;
import net.ufrog.common.app.App;
import net.ufrog.common.cache.Caches;
import net.ufrog.leo.client.LeoConfig;
import net.ufrog.leo.client.api.APIs;
import net.ufrog.leo.client.api.beans.AppUserResp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 索引控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-04-17
 * @since 0.1
 */
@Controller
public class IndexController {

    /**
     * 索引
     *
     * @return view for index.html
     */
    @GetMapping({"", "/", "/index"})
    public String index() {
        return "index";
    }

    /**
     * 注销
     *
     * @return view for sign
     */
    @GetMapping("/sign_out")
    public String signOut() {
        return "sign";
    }

    /**
     * 清空缓存
     *
     * @return 清空结果
     */
    @GetMapping("/clear")
    public String clear(Model model) {
        Caches.clear();
        model.addAttribute("result", Result.success(App.message("cache.clear.success")));
        return "result";
    }

    /**
     * 简单视图渲染
     *
     * @param view 视图位置
     * @return view for view.html
     */
    @GetMapping("/_v/{view}")
    public String view(@PathVariable("view") String view) {
        return view.replaceAll("@", "/");
    }

    @GetMapping("/test")
    public void test() {
        AppUserResp aur = APIs.getUserByOpenPlatform(LeoConfig.getLeoAppId(), "22609837@qq.com", "13564923886", "钱嘎嘎", false, true, "test_id", "12345678");
        Logger.info(JSON.toJSONString(aur));
    }
}

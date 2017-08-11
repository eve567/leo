package net.ufrog.leo.console.controllers;

import net.ufrog.common.Result;
import net.ufrog.common.cache.Caches;
import net.ufrog.leo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 索引控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-04-17
 * @since 0.1
 */
@Controller
public class IndexController {

    private TestService testService;

    @Autowired
    public IndexController(TestService testService) {
        this.testService = testService;
    }

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
    public Result<?> clear() {
        Caches.clear();
        return Result.success("success to clear all cache.");
    }

    @GetMapping("/test")
    public void test() {
        testService.testUtf8Len();
    }
}

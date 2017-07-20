package net.ufrog.leo.console.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    /**
     * 索引
     *
     * @return view for nav.index
     */
    @GetMapping({"", "/", "/index"})
    public String index() {
        return "nav/index";
    }
}

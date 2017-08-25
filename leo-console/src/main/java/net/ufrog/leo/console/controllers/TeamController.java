package net.ufrog.leo.console.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 团队控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-25
 * @since 0.1
 */
@Controller
@RequestMapping("/team")
public class TeamController {

    /**
     * 索引
     *
     * @return view for team/index
     */
    @GetMapping({"", "/", "/index"})
    public String index() {
        return "team/index";
    }
}

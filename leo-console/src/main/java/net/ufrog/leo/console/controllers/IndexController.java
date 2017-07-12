package net.ufrog.leo.console.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.IntStream;

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
     * @return view for index
     */
    @GetMapping({"", "/", "/index"})
    public String index() {
        return "index";
    }
}

package net.ufrog.leo.console.controllers;

import net.ufrog.leo.domain.models.User;
import net.ufrog.leo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 用户控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-25
 * @since 0.1
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /** 用户业务接口 */
    private final UserService userService;

    /**
     * 构造函数
     *
     * @param userService 用户业务接口
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 查询用户
     *
     * @param types 类型数组
     * @return 用户列表
     */
    @GetMapping("/list_all")
    @ResponseBody
    public List<User> findAll(String[] types) {
        return userService.findAll(types);
    }

    /**
     * 查询用户
     *
     * @param page 当前页码
     * @param size 分页大小
     * @param types 类型数组
     * @return 用户分页信息
     */
    @GetMapping("/find_all")
    @ResponseBody
    public Page<User> findAll(Integer page, Integer size, String[] types) {
        return userService.findAll(page, size, types);
    }
}

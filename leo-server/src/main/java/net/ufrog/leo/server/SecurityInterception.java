package net.ufrog.leo.server;

import net.ufrog.common.app.App;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.models.User;
import net.ufrog.leo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-22
 * @since 0.1
 */
public class SecurityInterception implements HandlerInterceptor {

    /** 用户业务接口 */
    private UserService userService;

    /** 强制修改密码地址 */
    private String forcedResetPasswordUri;

    /**
     * 构造函数
     *
     * @param userService 用户业务接口
     */
    @Autowired
    public SecurityInterception(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        User  user = userService.findById(App.user().getId());
        if (Strings.equals(User.Forced.TRUE, user.getForced())) {
            httpServletRequest.getRequestDispatcher(getForcedResetPasswordUri()).forward(httpServletRequest, httpServletResponse);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {}

    /**
     * 读取强制修改密码地址
     *
     * @return 强制修改密码地址
     */
    public String getForcedResetPasswordUri() {
        return forcedResetPasswordUri;
    }

    /**
     * 设置强制修改密码地址
     *
     * @param forcedResetPasswordUri 强制修改密码地址
     */
    public void setForcedResetPasswordUri(String forcedResetPasswordUri) {
        this.forcedResetPasswordUri = forcedResetPasswordUri;
    }
}

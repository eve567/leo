package net.ufrog.leo.client;

import net.ufrog.common.app.App;
import net.ufrog.common.app.AppUser;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.spring.app.SpringWebApp;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-06
 * @since 0.1
 */
public class LeoApp extends SpringWebApp {

    /**
     * 构造函数
     *
     * @param request 请求
     * @param response 响应
     */
    private LeoApp(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public AppUser getUser() {
        //TODO
        return null;
    }

    @Override
    public AppUser setUser(AppUser user) {
        throw new ServiceException("the method is not support!");
    }

    @Override
    public String getMessage(String key, Object... args) {
        return super.getMessage(key, args);
    }

    /**
     * @return leo url
     */
    public String getUrl() {
        return LeoConfig.getLeoHost();
    }

    /**
     * @return leo app id
     */
    public String getAppId() {
        return LeoConfig.getLeoAppId();
    }

    /**
     * @return leo app security
     */
    public String getAppSecret() {
        return LeoConfig.getLeoAppSecret();
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    public static void initialize(ServletContext context) {
        SpringWebApp.initialize(context);
    }

    /**
     * 创建实例
     *
     * @param request 请求
     * @param response 响应
     * @return 当前线程应用
     */
    public static App create(HttpServletRequest request, HttpServletResponse response) {
        current(new LeoApp(request, response));
        return current();
    }
}

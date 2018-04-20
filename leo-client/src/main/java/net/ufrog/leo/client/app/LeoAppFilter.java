package net.ufrog.leo.client.app;

import net.ufrog.common.Logger;
import net.ufrog.common.app.App;
import net.ufrog.common.utils.Strings;
import net.ufrog.common.web.RequestParam;
import net.ufrog.common.web.app.WebAppFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户中心应用过滤器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-11
 * @since 3.0.0
 */
public class LeoAppFilter implements Filter {

    public static final String PARAM_KEY = "_leo_access_token";

    @Override
    public void init(FilterConfig filterConfig) {
        Logger.info("initialize leo application filter.");
        LeoApp.initialize(filterConfig.getServletContext());
        Logger.info("leo application filter is running...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 创建当前线程实例
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        LeoApp leoApp = LeoApp.create(httpServletRequest, httpServletResponse);
        servletRequest.setAttribute(WebAppFilter.PARAM_KEY, leoApp);

        // 判断是否存在跳转
        RequestParam requestParam = leoApp.getRequestParam();
        if (requestParam.getParams().containsKey(PARAM_KEY)) {
            String paramStr = requestParam.getParamString(PARAM_KEY);

            leoApp.setAccessToken(requestParam.getValue(PARAM_KEY));
            Logger.info("received user request from leo...");
            Logger.debug("received user token: %s", requestParam.getValue(PARAM_KEY));
            httpServletResponse.sendRedirect(httpServletRequest.getRequestURL() + (Strings.empty(paramStr) ? "" : "?") + paramStr);
            return;
        }

        // 判断用户是否有效
        App.user();

        // 继续执行过滤器链
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Logger.info("destroyed leo application filter!");
    }
}

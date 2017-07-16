package net.ufrog.leo.client;

import net.ufrog.common.Logger;
import net.ufrog.common.web.app.WebAppFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-06
 * @since 0.1
 */
public class LeoAppFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Logger.info("initialize leo application filter.");
        LeoApp.initialize(filterConfig.getServletContext());
        Logger.info("leo application filter is running...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        servletRequest.setAttribute(WebAppFilter.PARAM_KEY, LeoApp.create(httpServletRequest, httpServletResponse));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Logger.info("destroyed leo application filter!");
    }
}

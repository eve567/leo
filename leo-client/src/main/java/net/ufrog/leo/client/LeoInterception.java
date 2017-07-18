package net.ufrog.leo.client;

import net.ufrog.common.Logger;
import net.ufrog.common.app.App;
import net.ufrog.common.web.RequestParam;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-07
 * @since 0.1
 */
public class LeoInterception implements HandlerInterceptor {

    public static final String DEFAULT_PARAM_KEY   = "_leo_access_token";

    /** 参数名 */
    private String paramKey = DEFAULT_PARAM_KEY;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        RequestParam requestParam = RequestParam.current();
        if (requestParam.getParams().containsKey(getParamKey())) {
            Logger.info("received user request from leo...");
            App.current(LeoApp.class).setAccessToken(requestParam.getValue(getParamKey()));
        }
        App.user();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {}

    /**
     * 读取参数名
     *
     * @return 参数名
     */
    public String getParamKey() {
        return paramKey;
    }

    /**
     * 设置参数名
     *
     * @param paramKey 参数名
     */
    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }
}

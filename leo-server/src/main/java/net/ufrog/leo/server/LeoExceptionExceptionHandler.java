package net.ufrog.leo.server;

import net.ufrog.common.spring.exception.ExceptionHandler;
import net.ufrog.common.spring.exception.ExceptionLogger;
import net.ufrog.common.spring.exception.ExceptionResolver;
import net.ufrog.leo.client.LeoException;
import net.ufrog.leo.client.api.beans.Resp;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-07-20
 * @since 0.1
 */
public class LeoExceptionExceptionHandler implements ExceptionHandler<LeoException> {

    @Override
    public Boolean isType(Exception e) {
        return e.getClass() == LeoException.class;
    }

    @Override
    public ModelAndView handle(LeoException e, String requestType, String errorView, String partViewSuffix, View jsonView, ExceptionLogger exceptionLogger) {
        return ExceptionResolver.modelAndView(ExceptionResolver.REQUEST_TYPE_JSON, errorView, partViewSuffix, jsonView, new Resp(e.getRespCode()));
    }
}

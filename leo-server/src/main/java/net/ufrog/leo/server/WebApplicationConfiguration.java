package net.ufrog.leo.server;

import net.ufrog.common.app.App;
import net.ufrog.common.spring.exception.ExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
import java.util.Optional;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-07-17
 * @since 0.1
 */
@Configuration
public class WebApplicationConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public AccessTokenManager accessTokenManager() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return AccessTokenManager.class.cast(Class.forName(App.config("leo.accessTokenManager")).newInstance());
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        Optional<HandlerExceptionResolver> oHandlerExceptionResolver = exceptionResolvers.stream().filter(exceptionResolver -> exceptionResolver.getClass() == ExceptionResolver.class).findFirst();
        if (oHandlerExceptionResolver.isPresent()) {
            ExceptionResolver exceptionResolver = ExceptionResolver.class.cast(oHandlerExceptionResolver.get());
            exceptionResolver.getExceptionHandlers().add(new LeoExceptionExceptionHandler());
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityInterception()).excludePathPatterns("/sign", "/sign_in", "/sign_out", "/authenticate", "/api/**");
    }
}

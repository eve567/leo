package net.ufrog.leo.server;

import net.ufrog.common.app.App;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityInterception()).excludePathPatterns("/sign", "/sign_in", "/sign_out", "/authenticate", "/api/**");
    }
}

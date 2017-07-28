package net.ufrog.leo.console;

import net.ufrog.leo.client.LeoInterception;
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LeoInterception());
    }
}
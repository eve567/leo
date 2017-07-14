package net.ufrog.leo.server.configurations;

import net.ufrog.common.app.App;
import net.ufrog.common.spring.SpringConfigurations;
import net.ufrog.common.spring.fastjson.FastJsonpHttpMessageConverter;
import net.ufrog.leo.server.AccessTokenManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-07-14
 * @since 0.1
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "net.ufrog.leo", excludeFilters = @ComponentScan.Filter(Controller.class))
public class ContextConfiguration {

    @Bean
    public MessageSource messageSource() {
        return SpringConfigurations.reloadableResourceBundleMessageSource();
    }

    @Bean
    public FastJsonpHttpMessageConverter jsonConverter() {
        return SpringConfigurations.fastJsonpHttpMessageConverter();
    }

    @Bean
    public AccessTokenManager accessTokenManager() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return AccessTokenManager.class.cast(Class.forName(App.config("leo.accessTokenManage")).newInstance());
    }
}

package net.ufrog.leo.client.configuration;

import net.ufrog.common.spring.springboot.SpringWebAppConfiguration;
import net.ufrog.leo.client.app.LeoApp;
import net.ufrog.leo.client.app.LeoAppFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-17
 * @since 3.0.0
 */
@ConditionalOnProperty(value = "ufrog.leo.enabled", havingValue = "true")
@EnableConfigurationProperties(SpringWebAppConfiguration.AppProperties.class)
public class LeoAppConfiguration {

    @SuppressWarnings("unchecked")
    @Bean
    @ConditionalOnWebApplication
    @ConditionalOnClass(LeoApp.class)
    @ConditionalOnMissingBean(LeoAppFilter.class)
    public FilterRegistrationBean leoAppFilter(SpringWebAppConfiguration.AppProperties appProperties) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        LeoApp.setConfigHelper(appProperties.get());
        filterRegistrationBean.setFilter(new LeoAppFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("leoAppFilter");
        return filterRegistrationBean;
    }
}

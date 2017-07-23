package net.ufrog.leo.service.configurations;

import net.ufrog.common.jetbrick.SpringJetxConfigurations;
import net.ufrog.common.spring.SpringConfigurations;
import net.ufrog.common.spring.exception.ExceptionLogger;
import net.ufrog.common.spring.fastjson.FastJsonpHttpMessageConverter;
import net.ufrog.common.spring.interceptor.TokenInterceptor;
import net.ufrog.leo.service.PropService;
import net.ufrog.leo.service.beans.DatabasePropertiesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.List;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-07-17
 * @since 0.1
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "net.ufrog.leo", useDefaultFilters = false, includeFilters = @ComponentScan.Filter(Controller.class))
public class WebConfiguration extends WebMvcConfigurerAdapter {

    private final FastJsonpHttpMessageConverter fastJsonpHttpMessageConverter;
    private final PropService propService;

    @Autowired
    public WebConfiguration(FastJsonpHttpMessageConverter fastJsonpHttpMessageConverter, PropService propService) {
        this.fastJsonpHttpMessageConverter = fastJsonpHttpMessageConverter;
        this.propService = propService;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonpHttpMessageConverter);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(SpringJetxConfigurations.jetTemplateViewResolver());
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        String errorView = "result";
        String partViewSuffix = "";
        String notSignView = "sign";
        String notSignUri = "/sign_out";
        String notSignPrefix = "_not_sign::";
        exceptionResolvers.add(SpringConfigurations.exceptionResolver(errorView, partViewSuffix, null, notSignView, notSignUri, notSignPrefix));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor());
        registry.addInterceptor(new TokenInterceptor());
        registry.addInterceptor(SpringConfigurations.propertiesInterceptor(new DatabasePropertiesManager(propService)));
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    @Bean
    public SessionLocaleResolver localeResolver() {
        return SpringConfigurations.sessionLocaleResolver();
    }
}

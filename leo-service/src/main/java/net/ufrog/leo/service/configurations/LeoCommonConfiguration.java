package net.ufrog.leo.service.configurations;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import net.ufrog.common.spring.SpringConfigurations;
import net.ufrog.common.spring.fastjson.FastJsonpHttpMessageConverter;
import net.ufrog.common.spring.interceptor.MultipartRequestInterceptor;
import net.ufrog.common.spring.interceptor.PropertiesInterceptor;
import net.ufrog.common.spring.interceptor.TokenInterceptor;
import net.ufrog.common.spring.springboot.AppAutoConfiguration;
import net.ufrog.leo.domain.repositories.BlobRepository;
import net.ufrog.leo.service.PropService;
import net.ufrog.leo.service.storages.DBStorage;
import net.ufrog.leo.service.storages.Storage;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-07-17
 * @since 0.1
 */
@Configuration
@EnableConfigurationProperties(AppAutoConfiguration.AppProperties.class)
public class LeoCommonConfiguration extends WebMvcConfigurerAdapter {

    /**  */
    private AppAutoConfiguration.AppProperties appProperties;

    /** 系统属性业务接口 */
    private PropService propService;

    /**
     * 构造函数
     *
     * @param appProperties 应用属性
     * @param propService 系统属性业务接口
     */
    public LeoCommonConfiguration(AppAutoConfiguration.AppProperties appProperties, PropService propService) {
        this.appProperties = appProperties;
        this.propService = propService;
    }

    @Bean
    public Storage storage(BlobRepository blobRepository) {
        return new DBStorage(blobRepository);
    }

    @Bean
    public PropertiesInterceptor propertiesInterceptor() {
        return SpringConfigurations.propertiesInterceptor(new DBPropertiesManager(propService));
    }

    @Bean
    public HttpMessageConverter jsonConverter() {
        FastJsonpHttpMessageConverter fastJsonpHttpMessageConverter = new FastJsonpHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        List<MediaType> lMediaType = new ArrayList<>();

        lMediaType.add(new MediaType("application", "json"));
        lMediaType.add(new MediaType("text", "javascript"));

        fastJsonConfig.setSerializerFeatures(SerializerFeature.BrowserCompatible, SerializerFeature.DisableCircularReferenceDetect);
        fastJsonpHttpMessageConverter.setSupportedMediaTypes(lMediaType);
        fastJsonpHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        fastJsonpHttpMessageConverter.setJsonp("callback");
        return fastJsonpHttpMessageConverter;
    }

    @Bean
    public MessageSource messageSource() {
        return SpringConfigurations.reloadableResourceBundleMessageSource(appProperties.getConfig().getProperty("app.messages"));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(propertiesInterceptor());
        registry.addInterceptor(new MultipartRequestInterceptor());
        registry.addInterceptor(new TokenInterceptor());
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(SpringConfigurations.exceptionResolver(
                "result",
                "",
                null,
                "sign",
                appProperties.getConfig().getProperty("leo.host") + "/sign_out",
                "_not_sign::"
        ));
    }
}

package net.ufrog.leo.service.configurations;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import net.ufrog.common.spring.SpringConfigurations;
import net.ufrog.common.spring.fastjson.FastJsonpHttpMessageConverter;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-07-17
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
}

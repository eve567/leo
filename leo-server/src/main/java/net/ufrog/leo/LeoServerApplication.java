package net.ufrog.leo;

import net.ufrog.aries.common.exception.AriesExceptionHandler;
import net.ufrog.common.spring.exception.ExceptionResolver;
import net.ufrog.leo.server.LeoInterception;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Optional;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-10
 * @since 3.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableSwagger2
public class LeoServerApplication extends WebMvcConfigurerAdapter {

    /**
     * @param args argument array
     */
    public static void main(String[] args) {
        SpringApplication.run(LeoServerApplication.class, args);
    }

    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("net.ufrog.leo.server.controller")).build();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LeoInterception()).excludePathPatterns("/sign", "/sign_in", "/sign_out", "/authenticate");
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        Optional<HandlerExceptionResolver> oHandlerExceptionResolver = exceptionResolvers.stream().filter(exceptionResolver -> exceptionResolver.getClass() == ExceptionResolver.class).findFirst();
        if (oHandlerExceptionResolver.isPresent()) {
            ExceptionResolver exceptionResolver = ExceptionResolver.class.cast(oHandlerExceptionResolver.get());
            exceptionResolver.getExceptionHandlers().add(new AriesExceptionHandler());
        }
    }
}

package net.ufrog.leo.server;

import net.ufrog.aries.common.exception.AriesExceptionHandler;
import net.ufrog.common.spring.exception.ExceptionHandler;
import net.ufrog.common.spring.exception.ExceptionResolver;
import net.ufrog.leo.client.configuration.LeoInterception;
import net.ufrog.leo.client.configuration.LeoProperties;
import net.ufrog.leo.domain.jpqls.SecurityJpql;
import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.domain.repositories.AppRepository;
import net.ufrog.leo.server.accesstoken.AccessTokenManager;
import net.ufrog.leo.service.AppService;
import net.ufrog.leo.service.configurations.LeoCommonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-10
 * @since 3.0.0
 */
@SpringBootApplication(scanBasePackageClasses = {LeoServerApplication.class, AppService.class, SecurityJpql.class})
@EntityScan(basePackageClasses = App.class)
@EnableJpaRepositories(basePackageClasses = AppRepository.class)
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableSwagger2
@EnableConfigurationProperties(LeoProperties.class)
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

    @Bean
    public AccessTokenManager accessTokenManager(LeoProperties leoProperties) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return AccessTokenManager.class.cast(Class.forName(leoProperties.getAccessTokenManager()).newInstance());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LeoInterception()).excludePathPatterns("/sign", "/sign_in", "/sign_out", "/authenticate", "/api/**", "/swagger-ui.html");
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        List<ExceptionHandler> lExceptionHandler = new ArrayList<>(1);
        lExceptionHandler.add(new AriesExceptionHandler());
        LeoCommonConfiguration.setExceptionHandler(lExceptionHandler, exceptionResolvers);
    }
}

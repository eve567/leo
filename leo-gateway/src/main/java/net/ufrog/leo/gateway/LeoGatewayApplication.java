package net.ufrog.leo.gateway;

import net.ufrog.leo.client.configuration.LeoInterception;
import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.domain.repositories.AppRepository;
import net.ufrog.leo.service.AppService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-05-15
 * @since 3.0.0
 */
@SpringBootApplication(scanBasePackageClasses = {LeoGatewayApplication.class, AppService.class})
@EntityScan(basePackageClasses = App.class)
@EnableJpaRepositories(basePackageClasses = AppRepository.class)
@EnableDiscoveryClient
@EnableHystrix
public class LeoGatewayApplication implements WebMvcConfigurer {

    /**
     * @param args argument array
     */
    public static void main(String[] args) {
        SpringApplication.run(LeoGatewayApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LeoInterception()).excludePathPatterns("/sign", "/sign_in", "/sign_out", "/authenticate");
    }
}

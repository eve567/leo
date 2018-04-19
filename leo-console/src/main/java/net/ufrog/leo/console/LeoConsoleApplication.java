package net.ufrog.leo.console;

import net.ufrog.leo.client.LeoClient;
import net.ufrog.leo.client.fallback.LeoClientFallbackFactory;
import net.ufrog.leo.domain.jpqls.SecurityJpql;
import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.domain.repositories.AppRepository;
import net.ufrog.leo.service.AppService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-17
 * @since 3.0.0
 */
@SpringBootApplication(scanBasePackageClasses = {LeoConsoleApplication.class, AppService.class, SecurityJpql.class, LeoClientFallbackFactory.class})
@EntityScan(basePackageClasses = App.class)
@EnableJpaRepositories(basePackageClasses = AppRepository.class)
@EnableFeignClients(basePackageClasses = {LeoClient.class})
@EnableDiscoveryClient
@EnableHystrix
public class LeoConsoleApplication {

    /**
     * @param args argument array
     */
    public static void main(String[] args) {
        SpringApplication.run(LeoConsoleApplication.class, args);
    }
}

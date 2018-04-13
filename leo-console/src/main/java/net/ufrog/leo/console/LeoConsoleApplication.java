package net.ufrog.leo.console;

import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.domain.repositories.AppRepository;
import net.ufrog.leo.service.AppService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-09
 * @since 3.0.0
 */
@SpringBootApplication
@EntityScan(basePackageClasses = App.class)
@EnableJpaRepositories(basePackageClasses = AppRepository.class)
@ComponentScan(basePackageClasses = AppService.class)
@EnableDiscoveryClient
public class LeoConsoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeoConsoleApplication.class, args);
    }
}

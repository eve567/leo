package net.ufrog.leo.service.configurations;

import net.ufrog.leo.domain.repositories.BlobRepository;
import net.ufrog.leo.service.storages.DBStorage;
import net.ufrog.leo.service.storages.Storage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;

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
    public Storage storage(BlobRepository blobRepository) {
        return new DBStorage(blobRepository);
    }
}

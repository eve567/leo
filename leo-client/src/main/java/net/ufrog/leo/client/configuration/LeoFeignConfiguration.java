package net.ufrog.leo.client.configuration;

import net.ufrog.leo.client.AppClient;
import net.ufrog.leo.client.fallbackfactories.AppClientFallbackFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-24
 * @since 3.0.0
 */
@EnableFeignClients(basePackageClasses = AppClient.class)
@ComponentScan(basePackageClasses = AppClientFallbackFactory.class)
@ConditionalOnMissingClass("net.ufrog.leo.server.controllers.AppController")
public class LeoFeignConfiguration {
}

package net.ufrog.leo.console;

import net.ufrog.common.spring.SpringConfigurations;
import net.ufrog.common.utils.Codecs;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.client.LeoAppFilter;
import net.ufrog.leo.service.configurations.ContextConfiguration;
import net.ufrog.leo.service.configurations.WebConfiguration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-07-16
 * @since 0.1
 */
public class WebApplicationInitializer implements org.springframework.web.WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        SpringConfigurations.addContextLoaderListener(servletContext, ContextConfiguration.class);
        SpringConfigurations.addDispatcherServlet(servletContext, "/", WebConfiguration.class);
        SpringConfigurations.addCharacterEncodingFilter(servletContext, "/*");
        SpringConfigurations.addHttpPutFormContentFilter(servletContext, "/*");

        servletContext.addFilter(Codecs.uuid(), LeoAppFilter.class).addMappingForUrlPatterns(null, false, "/*");
    }
}

package net.ufrog.leo.console.loaders;

import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.domain.models.Resource;
import net.ufrog.leo.service.AppService;
import net.ufrog.leo.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用资源加载实现
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-22
 * @since 0.1
 */
@Component("resource_99")
public class AppResourceLoader implements ResourceLoader {

    /** 应用业务接口 */
    private final AppService appService;

    /** 权限业务接口 */
    private final SecurityService securityService;

    /**
     * 构造函数
     *
     * @param appService 应用业务接口
     * @param securityService 权限业务接口
     */
    @Autowired
    public AppResourceLoader(AppService appService, SecurityService securityService) {
        this.appService = appService;
        this.securityService = securityService;
    }

    @Override
    public List<ResourceWrapper> load() {
        List<App> lApp = appService.findAll();
        List<ResourceWrapper> lResourceWrapper = new ArrayList<>(lApp.size());

        lApp.forEach(app -> {
            Resource resource = securityService.findResourceByTypeAndReferenceId(Resource.Type.APP, app.getId());
            lResourceWrapper.add(new ResourceWrapper(app.getId(), app.getName() + " - " + app.getCode(), resource.getId()));
        });
        return lResourceWrapper;
    }
}

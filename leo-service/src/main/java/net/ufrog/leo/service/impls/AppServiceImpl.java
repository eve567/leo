package net.ufrog.leo.service.impls;

import net.ufrog.common.cache.Caches;
import net.ufrog.common.data.spring.Domains;
import net.ufrog.common.utils.Objects;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.domain.models.Resource;
import net.ufrog.leo.domain.repositories.AppRepository;
import net.ufrog.leo.service.AppService;
import net.ufrog.leo.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 应用业务实现
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-16
 * @since 0.1
 */
@Service
@Transactional(readOnly = true)
public class AppServiceImpl implements AppService {

    private static final String CACHE_APP   = "app_";
    private static final String CACHE_APPS  = "apps";

    /** 应用仓库 */
    private AppRepository appRepository;

    /** 权限业务接口 */
    private SecurityService securityService;

    /**
     * 构造函数
     *
     * @param appRepository 应用仓库
     * @param securityService 权限业务接口
     */
    @Autowired
    public AppServiceImpl(AppRepository appRepository, SecurityService securityService) {
        this.appRepository = appRepository;
        this.securityService = securityService;
    }

    @Override
    public App findById(String id) {
        return appRepository.findOne(id);
    }

    @Override
    public App getById(String id) {
        App app = Caches.get(CACHE_APP, id, App.class);
        if (app == null) {
            app = findById(id);
            if (app != null) Caches.set(CACHE_APP, id, app);
        }
        return app;
    }

    @Override
    public List<App> findAll() {
        return appRepository.findAll(Domains.sort(Sort.Direction.ASC, "code"));
    }

    @Override
    public List<App> getAll() {
        @SuppressWarnings("unchecked") List<App> lApp = Caches.get(CACHE_APPS, List.class);
        if (lApp == null) {
            lApp = findAll();
            Caches.set(CACHE_APPS, lApp);
        }
        return lApp;
    }

    @Override
    public Page<App> findAll(Integer page, Integer size) {
        Pageable pageable = Domains.pageable(page, size, Domains.sort(Sort.Direction.ASC, "status", "code"));
        return appRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public App create(App app) {
        app.setSecret(Strings.random(64));
        appRepository.save(app);
        securityService.createResource(Resource.Type.APP, app.getId());
        clearAll();
        return app;
    }

    @Override
    @Transactional
    public App update(App app) {
        App oApp = appRepository.findOne(app.getId());
        Objects.copyProperties(oApp, app, Boolean.TRUE, "id", "creator", "createTime", "updater", "updateTime");
        clearAll();
        return appRepository.save(oApp);
    }

    @Override
    public void clear(String id) {
        Caches.safeDelete(CACHE_APP, id);
    }

    @Override
    public void clearAll() {
        Caches.safeDelete(CACHE_APPS);
    }
}

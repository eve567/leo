package net.ufrog.leo.service.impls;

import net.ufrog.common.cache.Caches;
import net.ufrog.common.data.spring.Domains;
import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.domain.repositories.AppRepository;
import net.ufrog.leo.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /**
     * 构造函数
     *
     * @param appRepository 应用仓库
     */
    @Autowired
    public AppServiceImpl(AppRepository appRepository) {
        this.appRepository = appRepository;
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
        @SuppressWarnings("unchecked")
        List<App> lApp = Caches.get(CACHE_APPS, List.class);
        if (lApp == null) {
            lApp = findAll();
            Caches.set(CACHE_APPS, lApp);
        }
        return lApp;
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

package net.ufrog.leo.service.impls;

import net.ufrog.common.Logger;
import net.ufrog.common.cache.Caches;
import net.ufrog.common.data.spring.Domains;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Objects;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.domain.models.AppResource;
import net.ufrog.leo.domain.models.Resource;
import net.ufrog.leo.domain.repositories.AppRepository;
import net.ufrog.leo.domain.repositories.AppResourceRepository;
import net.ufrog.leo.service.AppService;
import net.ufrog.leo.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    /** 应用资源仓库 */
    private AppResourceRepository appResourceRepository;

    /** 权限业务接口 */
    private SecurityService securityService;

    /**
     * 构造函数
     *
     * @param appRepository 应用仓库
     * @param appResourceRepository 应用资源仓库
     * @param securityService 权限业务接口
     */
    @Autowired
    public AppServiceImpl(AppRepository appRepository, AppResourceRepository appResourceRepository, SecurityService securityService) {
        this.appRepository = appRepository;
        this.appResourceRepository = appResourceRepository;
        this.securityService = securityService;
    }

    @Override
    public App findById(String id) {
        return appRepository.findById(id).orElse(null);
    }

    @Override
    public App getById(String id) {
        return Optional.ofNullable(Caches.get(CACHE_APP, id, App.class)).orElseGet(() -> Optional.ofNullable(findById(id)).map(app -> {
            Caches.set(CACHE_APP, id, app);
            return app;
        }).orElse(null));
    }

    @Override
    public List<App> findAll() {
        return appRepository.findByStatus(App.Status.ONLINE, Domains.sort(Sort.Direction.ASC, "code"));
    }

    @Override
    public List<App> getAll() {
        //noinspection unchecked
        return Optional.ofNullable(Caches.get(CACHE_APPS, List.class)).orElseGet(() -> {
            List<App> lApp = findAll();
            Caches.set(CACHE_APPS, lApp);
            return lApp;
        });
    }

    @Override
    public Page<App> findAll(Integer page, Integer size) {
        Pageable pageable = Domains.pageable(page, size, Domains.sort(Domains.order(Sort.Direction.DESC, "status"), Domains.order(Sort.Direction.ASC, "code")));
        return appRepository.findAll(pageable);
    }

    @Override
    public List<AppResource> findResourceTypes(String id) {
        return appResourceRepository.findByAppId(id);
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
        return appRepository.findById(app.getId()).map(oApp -> {
            Objects.copyProperties(oApp, app, Boolean.TRUE, "id", "creator", "createTime", "updater", "updateTime");
            clear(app.getId());
            clearAll();
            return appRepository.save(oApp);
        }).orElseThrow(() -> new ServiceException("cannot find app by id: " + app.getId()));
    }

    @Override
    @Transactional
    public List<AppResource> bindResourceTypes(String id, String[] types) {
        List<AppResource> lAppResource = appResourceRepository.findByAppId(id);
        List<AppResource> lsAppResource = Collections.synchronizedList(new ArrayList<>(types.length));

        Logger.info("delete %s app resource type reference(s).", lAppResource.size());
        appResourceRepository.deleteAll(lAppResource);
        Stream.of(types).parallel().forEach(type -> {
            AppResource appResource = new AppResource();
            appResource.setType(type);
            appResource.setAppId(id);
            lsAppResource.add(appResource);
        });
        return appResourceRepository.saveAll(lsAppResource);
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

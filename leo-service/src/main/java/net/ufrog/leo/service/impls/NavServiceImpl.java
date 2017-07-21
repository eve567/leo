package net.ufrog.leo.service.impls;

import net.ufrog.common.Link;
import net.ufrog.common.cache.Caches;
import net.ufrog.leo.domain.models.Nav;
import net.ufrog.leo.domain.repositories.NavRepository;
import net.ufrog.leo.service.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 导航业务实现
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-25
 * @since 0.1
 */
@Service
@Transactional(readOnly = true)
public class NavServiceImpl implements NavService {

    private static final String CACHE_NAV   = "nav_";

    /** 导航仓库 */
    private NavRepository navRepository;

    /**
     * 构造函数
     *
     * @param navRepository 导航仓库
     */
    @Autowired
    public NavServiceImpl(NavRepository navRepository) {
        this.navRepository = navRepository;
    }

    @Override
    public List<Nav> findRoot(String type, String appId) {
        return Link.sort(navRepository.findByTypeAndAppIdAndParentIdIsNull(type, appId));
    }

    @Override
    public List<Nav> getRoot(String type, String appId) {
        @SuppressWarnings("unchecked")
        List<Nav> lNav = Caches.get(CACHE_NAV, type + "-" + appId, List.class);
        if (lNav == null) {
            lNav = findRoot(type, appId);
            Caches.set(CACHE_NAV, type + "-" + appId, lNav);
        }
        return lNav;
    }

    @Override
    public List<Nav> findByParentId(String parentId) {
        return Link.sort(navRepository.findByParentId(parentId));
    }

    @Override
    public List<Nav> getByParentId(String parentId) {
        @SuppressWarnings("unchecked")
        List<Nav> lNav = Caches.get(CACHE_NAV, parentId, List.class);
        if (lNav == null) {
            lNav = findByParentId(parentId);
            Caches.set(CACHE_NAV, parentId, lNav);
        }
        return lNav;
    }

    @Override
    public void clearRoot(String type, String appId) {
        clear(type + "-" + appId);
    }

    @Override
    public void clear(String parentId) {
        Caches.safeDelete(CACHE_NAV, parentId);
    }
}

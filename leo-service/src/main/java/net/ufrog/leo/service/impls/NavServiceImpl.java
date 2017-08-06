package net.ufrog.leo.service.impls;

import net.ufrog.common.Link;
import net.ufrog.common.cache.Caches;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Objects;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.models.Nav;
import net.ufrog.leo.domain.models.Resource;
import net.ufrog.leo.domain.repositories.NavRepository;
import net.ufrog.leo.service.NavService;
import net.ufrog.leo.service.SecurityService;
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

    /** 权限业务接口 */
    private SecurityService securityService;

    /**
     * 构造函数
     *
     * @param navRepository 导航仓库
     * @param securityService 权限业务接口
     */
    @Autowired
    public NavServiceImpl(NavRepository navRepository, SecurityService securityService) {
        this.navRepository = navRepository;
        this.securityService = securityService;
    }

    @Override
    public List<Nav> findChildren(String type, String appId, String parentId) {
        return Link.sort(navRepository.findByTypeAndAppIdAndParentId(type, appId, parentId), Nav.LAST_KEY);
    }

    @Override
    public List<Nav> getChildren(String type, String appId, String parentId) {
        String key = getCacheKey(type, appId, parentId);
        @SuppressWarnings("unchecked") List<Nav> lNav = Caches.get(CACHE_NAV, key, List.class);
        if (lNav == null) {
            lNav = findChildren(type, appId, parentId);
            Caches.set(CACHE_NAV, key, lNav);
        }
        return lNav;
    }

    @Override
    @Transactional
    public Nav create(Nav nav) {
        List<Nav> lNav = navRepository.findByTypeAndAppIdAndParentIdAndCode(nav.getType(), nav.getAppId(), nav.getParentId(), nav.getCode());
        if (lNav.size() == 0) {
            Nav prev = navRepository.findByParentIdAndNextId(nav.getParentId(), nav.getNextId());
            navRepository.save(nav);
            if (prev != null) {
                prev.setNextId(nav.getId());
                navRepository.save(prev);
            }

            securityService.createResource(Resource.Type.NAV, nav.getId());
            clear(nav.getType(), nav.getAppId(), nav.getParentId());
            return nav;
        }
        throw new ServiceException("nav code '" + nav.getCode() + "' duplicate.", "service.nav.failure.duplicate");
    }

    @Override
    @Transactional
    public Nav update(Nav nav) {
        List<Nav> lNav = navRepository.findByTypeAndAppIdAndParentIdAndCode(nav.getType(), nav.getAppId(), nav.getParentId(), nav.getCode());
        Nav oNav = navRepository.findOne(nav.getId());
        if (lNav.size() == 0 || (lNav.size() == 1 && Strings.equals(nav.getId(), lNav.get(0).getId()))) {
            Nav prev = navRepository.findByParentIdAndNextId(nav.getParentId(), nav.getNextId());
            if (prev != null && !Strings.equals(prev.getId(), nav.getId())) {
                prev.setNextId(nav.getId());
                navRepository.save(prev);
            }

            Objects.copyProperties(oNav, nav, Boolean.TRUE, "id", "creator", "createTime", "updater", "updateTime");
            return navRepository.save(oNav);
        }
        throw new ServiceException("nav code '" + nav.getCode() + "' duplicate.", "service.nav.failure.duplicate");
    }

    @Override
    public void clear(String type, String appId, String parentId) {
        Caches.safeDelete(CACHE_NAV, getCacheKey(type, appId, parentId));
    }

    /**
     * 获取缓存键值
     *
     * @param type 类型
     * @param appId 应用编号
     * @param parentId 上级编号
     * @return 缓存键值
     */
    private String getCacheKey(String type, String appId, String parentId) {
        return type + "-" + appId + "-" + parentId;
    }
}

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
        // 检查代码是否重复
        checkDuplicate(nav);

        // 对前邻数据进行处理
        Nav prev = navRepository.findByParentIdAndNextIdAndAppId(nav.getParentId(), nav.getNextId(), nav.getAppId());
        navRepository.save(nav);
        if (prev != null) {
            prev.setNextId(nav.getId());
            navRepository.save(prev);
        }

        // 保存数据
        securityService.createResource(Resource.Type.NAV, nav.getId());
        clear(nav.getType(), nav.getAppId(), nav.getParentId());
        return nav;
    }

    @Override
    @Transactional
    public Nav update(Nav nav) {
        // 检查代码是否重复
        checkDuplicate(nav);

        // 对前后邻数据进行处理
        Nav oNav = navRepository.findOne(nav.getId());
        if (!Strings.equals(nav.getNextId(), oNav.getNextId())) {
            Nav nPrev = navRepository.findByParentIdAndNextIdAndAppId(nav.getParentId(), nav.getNextId(), nav.getAppId());
            Nav oPrev = navRepository.findByParentIdAndNextIdAndAppId(nav.getParentId(), nav.getId(), nav.getAppId());

            if (nPrev != null) {
                nPrev.setNextId(nav.getId());
                navRepository.save(nPrev);
            } if (oPrev != null) {
                oPrev.setNextId(oNav.getNextId());
                navRepository.save(oPrev);
            }
        }

        // 更新数据
        Objects.copyProperties(oNav, nav, Boolean.TRUE, "id", "creator", "createTime", "updater", "updateTime");
        clear(nav.getType(), nav.getAppId(), nav.getParentId());
        return navRepository.save(oNav);
    }

    @Override
    @Transactional
    public Nav delete(String id) {
        // 检查是否存在下级
        Nav nav = navRepository.findOne(id);
        checkChildren(nav);

        // 处理前邻数据
        Nav prev = navRepository.findByParentIdAndNextIdAndAppId(nav.getParentId(), nav.getId(), nav.getAppId());
        if (prev != null) {
            prev.setNextId(nav.getNextId());
            navRepository.save(prev);
        }

        // 删除数据
        navRepository.delete(nav);
        securityService.deleteResource(Resource.Type.NAV, nav.getId());
        clear(nav.getType(), nav.getAppId(), nav.getParentId());
        return nav;
    }

    @Override
    public void clear(String type, String appId, String parentId) {
        Caches.safeDelete(CACHE_NAV, getCacheKey(type, appId, parentId));
    }

    /**
     * 检查导航代码是否重复
     *
     * @param nav 待检查功能
     */
    private void checkDuplicate(Nav nav) {
        List<Nav> lNav = navRepository.findByTypeAndAppIdAndParentIdAndCode(nav.getType(), nav.getAppId(), nav.getParentId(), nav.getCode());
        if (lNav.size() > 1 || (lNav.size() == 1 && !Strings.equals(lNav.get(0).getId(), nav.getId()))) {
            throw new ServiceException("nav code '" + nav.getCode() + "' duplicate.", "service.nav.failure.duplicate");
        }
    }

    /**
     * 判断导航是否存在下级
     *
     * @param nav 导航对象
     */
    private void checkChildren(Nav nav) {
        List<Nav> children = findChildren(nav.getType(), nav.getAppId(), nav.getId());
        if (children != null && children.size() > 0) {
            throw new ServiceException("nav '" + nav.getName() + " - " + nav.getCode() + "' has children.", "service.nav.failure.has-children");
        }
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

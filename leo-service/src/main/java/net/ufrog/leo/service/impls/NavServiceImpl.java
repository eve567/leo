package net.ufrog.leo.service.impls;

import net.ufrog.common.Link;
import net.ufrog.common.Logger;
import net.ufrog.common.cache.Caches;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Objects;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.models.Nav;
import net.ufrog.leo.domain.models.Resource;
import net.ufrog.leo.domain.repositories.NavRepository;
import net.ufrog.leo.service.NavService;
import net.ufrog.leo.service.SecurityService;
import net.ufrog.leo.service.beans.ExportNav;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

        //noinspection unchecked
        return Optional.ofNullable(Caches.get(CACHE_NAV, key, List.class)).orElseGet(() -> {
            List<Nav> lNav = findChildren(type, appId, parentId);
            Caches.set(CACHE_NAV, key, lNav);
            return lNav;
        });
    }

    @Override
    @Transactional
    public Nav create(Nav nav) {
        checkDuplicate(nav);
        navRepository.save(nav);
        navRepository.findByParentIdAndNextIdAndAppId(nav.getParentId(), nav.getNextId(), nav.getAppId()).filter(prev -> !Strings.equals(nav.getId(), prev.getId())).ifPresent(prev -> setNextId(prev, nav.getId()));
        securityService.createResource(Resource.Type.NAV, nav.getId());
        clear(nav.getType(), nav.getAppId(), nav.getParentId());
        return nav;
    }

    @Override
    @Transactional
    public Nav update(Nav nav) {
        checkDuplicate(nav);
        return navRepository.findById(nav.getId()).map(oNav -> {
            if (!Strings.equals(nav.getNextId(), oNav.getNextId())) {
                navRepository.findByParentIdAndNextIdAndAppId(nav.getParentId(), nav.getNextId(), nav.getAppId()).ifPresent(prev -> setNextId(prev, nav.getId()));
                navRepository.findByParentIdAndNextIdAndAppId(nav.getParentId(), nav.getId(), nav.getAppId()).ifPresent(prev -> setNextId(prev, oNav.getNextId()));
            }
            Objects.copyProperties(oNav, nav, Boolean.TRUE, "id", "creator", "createTime", "updater", "updateTime");
            clear(nav.getType(), nav.getAppId(), nav.getParentId());
            return navRepository.save(oNav);
        }).orElseThrow(() -> new ServiceException("cannot find nav by id: " + nav.getId()));
    }

    @Override
    @Transactional
    public Nav delete(String id) {
        return navRepository.findById(id).map(nav -> {
            checkChildren(nav);
            navRepository.findByParentIdAndNextIdAndAppId(nav.getParentId(), nav.getId(), nav.getAppId()).ifPresent(prev -> setNextId(prev, nav.getNextId()));
            navRepository.delete(nav);
            securityService.deleteResource(Resource.Type.NAV, nav.getId());
            clear(nav.getType(), nav.getAppId(), nav.getParentId());
            return nav;
        }).orElseThrow(() -> new ServiceException("cannot find nav by id: " + id));
    }

    @Override
    public List<ExportNav> export(String appId, String parentId, String type) {
        List<Nav> lNav = findChildren(type, appId, parentId);
        if (lNav != null && lNav.size() > 0) {
            List<ExportNav> lExportNav = new ArrayList<>(lNav.size());
            lNav.forEach(nav -> {
                ExportNav exportNav = new ExportNav(nav.getCode(), nav.getName(), nav.getPath(), nav.getSubname(), nav.getTarget());
                exportNav.getChildren().addAll(export(appId, nav.getId(), type));
                lExportNav.add(exportNav);
            });
            return lExportNav;
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public void imports(List<ExportNav> lExportNav, String appId, String parentId, String type) {
        List<Nav> lNav = findChildren(type, appId, parentId);
        List<Nav> lTmp = new ArrayList<>(lExportNav.size());
        final Nav[] last = new Nav[1];

        //noinspection ComparatorMethodParameterNotUsed
        lExportNav.stream().sorted((o1, o2) -> -1).forEach(exportNav -> {
            Logger.info("handle nav with code %s", exportNav.getCode());
            Optional<Nav> oNav = lNav.stream().filter(nav -> Strings.equals(exportNav.getCode(), nav.getCode())).findFirst();
            Nav dest = oNav.orElse(new Nav());

            dest.setName(exportNav.getName());
            dest.setSubname(exportNav.getSubname());
            dest.setCode(exportNav.getCode());
            dest.setPath(exportNav.getPath());
            dest.setTarget(exportNav.getTarget());
            dest.setNextId((last[0] == null) ? Nav.LAST_KEY : last[0].getId());
            if (!oNav.isPresent()) {
                dest.setType(type);
                dest.setAppId(appId);
                dest.setParentId(parentId);
                navRepository.save(dest);
                securityService.createResource(Resource.Type.NAV, dest.getId());
                clear(dest.getType(), dest.getAppId(), dest.getParentId());
                Logger.info("cannot find nav with code %s, create new nav.", exportNav.getCode());
            } else {
                navRepository.save(dest);
            } if (exportNav.getChildren() != null && exportNav.getChildren().size() > 0) {
                imports(exportNav.getChildren(), appId, dest.getId(), type);
            }
            last[0] = dest;
            lTmp.add(dest);
        });
        lNav.removeAll(lTmp);
        deleteChildren(lNav);
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

    /**
     * 删除下级导航
     *
     * @param lNav 导航列表
     */
    private void deleteChildren(List<Nav> lNav) {
        lNav.parallelStream().forEach(nav -> {
            List<Nav> children = findChildren(nav.getType(), nav.getAppId(), nav.getId());
            if (children != null && children.size() > 0) {
                deleteChildren(children);
            }
            navRepository.delete(nav);
            securityService.deleteResource(Resource.Type.NAV, nav.getId());
            clear(nav.getType(), nav.getAppId(), nav.getParentId());
            Logger.info("delete nav id: %s, code: %s, name: %s", nav.getId(), nav.getCode(), nav.getName());
        });
    }

    /**
     * 设置下位编号
     *
     * @param nav 导航对象
     * @param nextId 下位编号
     */
    private void setNextId(Nav nav, String nextId) {
        nav.setNextId(nextId);
        navRepository.save(nav);
    }
}

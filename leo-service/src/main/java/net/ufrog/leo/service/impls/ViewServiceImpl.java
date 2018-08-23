package net.ufrog.leo.service.impls;

import net.ufrog.common.data.exception.DataNotFoundException;
import net.ufrog.common.data.spring.Domains;
import net.ufrog.common.utils.Objects;
import net.ufrog.leo.domain.models.Resource;
import net.ufrog.leo.domain.models.View;
import net.ufrog.leo.domain.models.ViewItem;
import net.ufrog.leo.domain.repositories.ViewItemRepository;
import net.ufrog.leo.domain.repositories.ViewRepository;
import net.ufrog.leo.service.SecurityService;
import net.ufrog.leo.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 视图业务实现
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 5.0.0, 2018-08-13
 * @since 5.0.0
 */
@Service
@Transactional(readOnly = true)
public class ViewServiceImpl implements ViewService {

    /** 视图仓库 */
    private final ViewRepository viewRepository;

    /** 视图元素仓库 */
    private final ViewItemRepository viewItemRepository;

    /** 权限业务接口 */
    private final SecurityService securityService;

    /**
     * 构造函数
     *  @param viewRepository 视图仓库
     * @param viewItemRepository 视图元素仓库
     * @param securityService 权限业务接口
     */
    @Autowired
    public ViewServiceImpl(ViewRepository viewRepository, ViewItemRepository viewItemRepository, SecurityService securityService) {
        this.viewRepository = viewRepository;
        this.viewItemRepository = viewItemRepository;
        this.securityService = securityService;
    }

    @Override
    public Page<View> read(String appId, Integer page, Integer size) {
        Pageable pageable = Domains.pageable(page, size, Sort.Direction.ASC, "code");
        return viewRepository.findByAppId(appId, pageable);
    }

    @Override
    public List<View> read(String appId) {
        return viewRepository.findByAppId(appId);
    }

    @Override
    @Transactional
    public View create(View view) {
        return viewRepository.save(view);
    }

    @Override
    @Transactional
    public View update(String id, View view) {
        return viewRepository.findById(id).map(oView -> {
            Objects.copyProperties(oView, view, Boolean.FALSE, "id", "creator", "createTime", "updater", "updateTime");
            return viewRepository.save(oView);
        }).orElseThrow(() -> new DataNotFoundException(View.class, "id", id));
    }

    @Override
    @Transactional
    public View delete(String id) {
        return viewRepository.findById(id).map(view -> {
            viewItemRepository.findByViewId(view.getId()).forEach(viewItem -> deleteItem(viewItem.getId()));
            viewRepository.delete(view);
            return view;
        }).orElseThrow(() -> new DataNotFoundException(View.class, "id", id));
    }

    @Override
    public List<ViewItem> readItems(String viewId) {
        return viewItemRepository.findByViewId(viewId, Domains.sort(Sort.Direction.ASC, "code"));
    }

    @Override
    public List<ViewItem> readItemsByViewCode(String appId, String code) {
        return viewRepository.findByAppIdAndCode(appId, code).map(view -> viewItemRepository.findByViewId(view.getId())).orElseThrow(() -> new DataNotFoundException(ViewItem.class, "appId", appId, "code", code));
    }

    @Override
    @Transactional
    public ViewItem createItem(ViewItem viewItem) {
        viewItemRepository.save(viewItem);
        securityService.createResource(Resource.Type.VIEW_ITEM, viewItem.getId());
        return viewItem;
    }

    @Override
    @Transactional
    public ViewItem deleteItem(String viewItemId) {
        return viewItemRepository.findById(viewItemId).map(viewItem -> {
            securityService.deleteResource(Resource.Type.VIEW_ITEM, viewItem.getId());
            viewItemRepository.delete(viewItem);
            return viewItem;
        }).orElseThrow(() -> new DataNotFoundException(ViewItem.class, "id", viewItemId));
    }
}

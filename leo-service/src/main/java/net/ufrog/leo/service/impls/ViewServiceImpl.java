package net.ufrog.leo.service.impls;

import net.ufrog.common.data.exception.DataNotFoundException;
import net.ufrog.common.data.spring.Domains;
import net.ufrog.common.utils.Objects;
import net.ufrog.leo.domain.models.View;
import net.ufrog.leo.domain.repositories.ViewRepository;
import net.ufrog.leo.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 构造函数
     *
     * @param viewRepository 视图仓库
     */
    @Autowired
    public ViewServiceImpl(ViewRepository viewRepository) {
        this.viewRepository = viewRepository;
    }

    @Override
    public Page<View> read(String appId, Integer page, Integer size) {
        Pageable pageable = Domains.pageable(page, size, Sort.Direction.ASC, "code");
        return viewRepository.findByAppId(appId, pageable);
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
            viewRepository.delete(view);
            return view;
        }).orElseThrow(() -> new DataNotFoundException(View.class, "id", id));
    }
}

package net.ufrog.leo.service.impls;

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
        return navRepository.findByTypeAndAppIdAndParentIdIsNull(type, appId);
    }

    @Override
    public List<Nav> findByParentId(String parentId) {
        return navRepository.findByParentId(parentId);
    }
}

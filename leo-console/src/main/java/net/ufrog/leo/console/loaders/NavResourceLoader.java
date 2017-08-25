package net.ufrog.leo.console.loaders;

import net.ufrog.common.web.RequestParam;
import net.ufrog.leo.domain.models.Nav;
import net.ufrog.leo.domain.models.Resource;
import net.ufrog.leo.service.NavService;
import net.ufrog.leo.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 导航资源加载实现
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-23
 * @since 0.1
 */
@Component("resource_00")
public class NavResourceLoader implements ResourceLoader {

    private static final String PARAM_KEY_TYPE      = "type";
    private static final String PARAM_KEY_PARENT_ID = "parentId";
    private static final String PARAM_KEY_APP_ID    = "appId";

    /** 导航业务接口 */
    private final NavService navService;

    /** 权限业务接口 */
    private final SecurityService securityService;

    /**
     * 构造函数
     *
     * @param navService 导航业务接口
     */
    @Autowired
    public NavResourceLoader(NavService navService, SecurityService securityService) {
        this.navService = navService;
        this.securityService = securityService;
    }

    @Override
    public List<ResourceWrapper> load() {
        RequestParam requestParam = RequestParam.current();
        String type = requestParam.getValue(PARAM_KEY_TYPE);
        String parentId = requestParam.getValue(PARAM_KEY_PARENT_ID);
        String appId = requestParam.getValue(PARAM_KEY_APP_ID);
        List<Nav> lNav = navService.findChildren(type, appId, parentId);
        List<ResourceWrapper> lResourceWrapper = new ArrayList<>(lNav.size());

        lNav.forEach(nav -> {
            Resource resource = securityService.findResourceByTypeAndReferenceId(Resource.Type.NAV, nav.getId());
            lResourceWrapper.add(new ResourceWrapper(nav.getId(), nav.getName(), resource.getId()));
        });
        return lResourceWrapper;
    }
}

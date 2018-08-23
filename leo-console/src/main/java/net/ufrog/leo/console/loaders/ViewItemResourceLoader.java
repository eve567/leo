package net.ufrog.leo.console.loaders;

import net.ufrog.aries.common.jpa.Model;
import net.ufrog.common.utils.Strings;
import net.ufrog.common.web.RequestParam;
import net.ufrog.leo.domain.models.Resource;
import net.ufrog.leo.service.SecurityService;
import net.ufrog.leo.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图元素资源加载实现
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 5.0.0, 2018-08-21
 * @since 5.0.0
 */
@Component("resource_98")
public class ViewItemResourceLoader implements ResourceLoader {

    private static final String PARAM_KEY_APP_ID    = "appId";
    private static final String PARAM_KEY_VIEW_ID   = "viewId";

    /** 视图业务接口 */
    private final ViewService viewService;

    /** 权限业务接口 */
    private final SecurityService securityService;

    /**
     * 构造函数
     *
     * @param viewService 视图业务接口
     * @param securityService 权限业务接口
     */
    @Autowired
    public ViewItemResourceLoader(ViewService viewService, SecurityService securityService) {
        this.viewService = viewService;
        this.securityService = securityService;
    }

    @Override
    public List<ResourceWrapper> load() {
        RequestParam requestParam = RequestParam.current();
        String appId = requestParam.getValue(PARAM_KEY_APP_ID);
        String viewId = requestParam.getValue(PARAM_KEY_VIEW_ID);
        List<ResourceWrapper> lResourceWrapper = new ArrayList<>();

        if (Strings.equals(Model.NULL, viewId)) {
            viewService.read(appId).forEach(view -> lResourceWrapper.add(new ResourceWrapper(view.getId(), view.getName() + " - " + view.getCode(), null)));
        } else {
            viewService.readItems(viewId).forEach(viewItem -> {
                Resource resource = securityService.findResourceByTypeAndReferenceId(Resource.Type.VIEW_ITEM, viewItem.getId());
                lResourceWrapper.add(new ResourceWrapper(viewItem.getId(), viewItem.getName() + " - " + viewItem.getCode(), resource.getId()));
            });
        }
        return lResourceWrapper;
    }
}

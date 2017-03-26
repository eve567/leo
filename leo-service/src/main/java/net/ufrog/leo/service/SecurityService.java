package net.ufrog.leo.service;

import net.ufrog.leo.domain.models.ResourceMarker;

import java.util.List;

/**
 * 权限业务接口
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-23
 * @since 0.1
 */
public interface SecurityService {

    /**
     * 权限过滤
     *
     * @param lResourceMarker 资源列表
     * @param userId
     * @param type
     * @return
     */
    List<ResourceMarker> filter(List<ResourceMarker> lResourceMarker, String userId, String type);
}

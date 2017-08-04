package net.ufrog.leo.service;

import net.ufrog.leo.domain.models.ID;
import net.ufrog.leo.domain.models.Resource;

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
     * 资源过滤
     *
     * @param lResource 元素列表
     * @param userId 用户编号
     * @param <T> 类型泛型
     * @return 过滤后的资源列表
     */
    <T extends ID> List<T> filter(List<T> lResource, String userId);

    /**
     * 创建资源
     *
     * @param type 类型
     * @param referenceId 相关编号
     * @return 资源
     */
    Resource createResource(String type, String referenceId);

    /**
     * 清空用户资源映射缓存
     *
     * @param userId 用户编号
     */
    void clearResourceMapping(String userId);
}

package net.ufrog.leo.service;

import net.ufrog.leo.domain.models.Nav;

import java.util.List;

/**
 * 导航业务接口
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-25
 * @since 0.1
 */
public interface NavService {

    /**
     * 查询下级导航
     *
     * @param type 类型
     * @param appId 应用编号
     * @param parentId 上级编号
     * @return 导航列表
     */
    List<Nav> findChildren(String type, String appId, String parentId);

    /**
     * 读取下级导航<br>缓存读取
     *
     * @param type 类型
     * @param appId 应用编号
     * @param parentId 上级编号
     * @return 导航列表
     */
    List<Nav> getChildren(String type, String appId, String parentId);

    /**
     * 创建导航
     *
     * @param nav 导航对象
     * @return 持久化导航对象
     */
    Nav create(Nav nav);

    /**
     * 清除导航缓存
     *
     * @param type 类型
     * @param appId 应用编号
     * @param parentId 上级编号
     */
    void clear(String type, String appId, String parentId);
}

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
     * 查询根级导航
     *
     * @param type 类型
     * @param appId 应用编号
     * @return 根导航列表
     */
    List<Nav> findRoot(String type, String appId);

    /**
     * 读取根级导航<br>缓存读取
     *
     * @param type 类型
     * @param appId 应用编号
     * @return 根导航列表
     */
    List<Nav> getRoot(String type, String appId);

    /**
     * 通过上级编号查询导航
     *
     * @param parentId 上级编号
     * @return 导航列表
     */
    List<Nav> findByParentId(String parentId);

    /**
     * 通过上级编号读取导航<br>缓存读取
     *
     * @param parentId 上级编号
     * @return 导航列表
     */
    List<Nav> getByParentId(String parentId);

    /**
     * 清除根级导航缓存
     *
     * @param type 类型
     * @param appId 应用编号
     */
    void clearRoot(String type, String appId);

    /**
     * 清除导航缓存
     *
     * @param parentId 上级编号
     */
    void clear(String parentId);
}

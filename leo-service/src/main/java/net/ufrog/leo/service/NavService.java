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
     * @param appId 应用编号
     * @param type 类型
     * @return 根导航列表
     */
    List<Nav> findRoot(String type, String appId);

    /**
     * 通过上级编号查询导航
     *
     * @param parentId 上级编号
     * @return 导航列表
     */
    List<Nav> findByParentId(String parentId);
}

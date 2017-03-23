package net.ufrog.leo.service;

import net.ufrog.leo.domain.models.App;

import java.util.List;

/**
 * 应用业务接口
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-16
 * @since 0.1
 */
public interface AppService {

    /**
     * 通过编号查询应用
     *
     * @param id 应用编号
     * @return 应用对象
     */
    App findById(String id);

    /**
     * 通过编号获取应用<br>缓存读写
     *
     * @param id 应用编号
     * @return 应用对象
     */
    App getById(String id);

    /**
     * 查询所有应用
     *
     * @return 应用列表
     */
    List<App> findAll();
}

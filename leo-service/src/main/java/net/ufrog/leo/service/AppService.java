package net.ufrog.leo.service;

import net.ufrog.leo.domain.models.App;
import org.springframework.data.domain.Page;

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

    /**
     * 读取所有应用<br>缓存读写
     *
     * @return 应用列表
     */
    List<App> getAll();

    /**
     * 查询所有应用<br>分页查询
     *
     * @param page 页码
     * @param size 分页大小
     * @return 分页应用数据
     */
    Page<App> findAll(Integer page, Integer size);

    /**
     * 创建应用
     *
     * @param app 应用对象
     * @return 持久化应用对象
     */
    App create(App app);

    /**
     * 更新应用
     *
     * @param app 应用对象
     * @return 持久化应用对象
     */
    App update(App app);

    /**
     * 清除应用缓存
     *
     * @param id 应用编号
     */
    void clear(String id);

    /** 清除所有应用缓存 */
    void clearAll();
}

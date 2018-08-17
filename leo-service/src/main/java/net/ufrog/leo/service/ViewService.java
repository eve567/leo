package net.ufrog.leo.service;

import net.ufrog.leo.domain.models.View;
import org.springframework.data.domain.Page;

/**
 * 视图业务接口
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 5.0.0, 2018-08-13
 * @since 5.0.0
 */
public interface ViewService {

    /**
     * 查询视图
     *
     * @param appId 应用编号
     * @param page 当前页码
     * @param size 分页大小
     * @return 视图分页对象
     */
    Page<View> read(String appId, Integer page, Integer size);

    /**
     * 创建视图
     *
     * @param view 视图对象
     * @return 持久化的视图对象
     */
    View create(View view);

    /**
     * 更新视图
     *
     * @param id 编号
     * @param view 视图对象
     * @return 更新后的视图对象
     */
    View update(String id, View view);

    /**
     * 删除视图
     *
     * @param id 编号
     * @return 视图对象
     */
    View delete(String id);
}

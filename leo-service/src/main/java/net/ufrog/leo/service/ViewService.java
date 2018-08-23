package net.ufrog.leo.service;

import net.ufrog.leo.domain.models.View;
import net.ufrog.leo.domain.models.ViewItem;
import org.springframework.data.domain.Page;

import java.util.List;

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
     * 查询视图
     *
     * @param appId 应用编号
     * @return 视图列表
     */
    List<View> read(String appId);

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

    /**
     * 查询视图元素
     *
     * @param viewId 视图编号
     * @return 视图元素列表
     */
    List<ViewItem> readItems(String viewId);

    /**
     * 通过视图编号查询视图元素
     *
     * @param appId 应用编号
     * @param code 视图代码
     * @return 视图元素列表
     */
    List<ViewItem> readItemsByViewCode(String appId, String code);

    /**
     * 创建视图元素
     *
     * @param viewItem 视图元素对象
     * @return 持久化的视图元素对象
     */
    ViewItem createItem(ViewItem viewItem);

    /**
     * 删除视图元素
     *
     * @param viewItemId 视图元素编号
     * @return 视图元素对象
     */
    ViewItem deleteItem(String viewItemId);
}

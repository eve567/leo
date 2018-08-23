package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.ViewItem;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 视图元素仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 5.0.0, 2018-08-13
 * @since 5.0.0
 */
@Repository
public interface ViewItemRepository extends JpaRepository<ViewItem, String> {

    /**
     * 通过视图编号查询视图元素
     *
     * @param viewId 视图编号
     * @return 视图元素列表
     */
    List<ViewItem> findByViewId(String viewId);

    /**
     * 通过视图编号查询视图元素
     *
     * @param viewId 视图编号
     * @param sort 排序
     * @return 视图元素列表
     */
    List<ViewItem> findByViewId(String viewId, Sort sort);
}

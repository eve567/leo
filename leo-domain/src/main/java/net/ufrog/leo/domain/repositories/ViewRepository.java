package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.View;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 视图仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 5.0.0, 2018-08-13
 * @since 5.0.0
 */
@Repository
public interface ViewRepository extends JpaRepository<View, String> {

    /**
     * 通过代码和应用编号查询视图
     *
     * @param appId 应用编号
     * @param code 代码
     * @return 视图
     */
    Optional<View> findByAppIdAndCode(String appId, String code);

    /**
     * 通过应用编号查询视图
     *
     * @param appId 应用编号
     * @param pageable 分页属性
     * @return 视图分页对象
     */
    Page<View> findByAppId(String appId, Pageable pageable);

    /**
     * 通过应用编号查询视图
     *
     * @param appId 应用编号
     * @return 视图列表
     */
    List<View> findByAppId(String appId);
}

package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.Nav;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 导航仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-25
 * @since 0.1
 */
@Repository
public interface NavRepository extends JpaRepository<Nav, String> {

    /**
     * 通过应用编号查询根级导航数据
     *
     * @param type 类型
     * @param appId 应用编号
     * @return 根级导航列表
     */
    List<Nav> findByTypeAndAppIdAndParentIdIsNull(String type, String appId);

    /**
     * 通过上级编号查询导航数据
     *
     * @param parentId 上级编号
     * @return 导航列表
     */
    List<Nav> findByParentId(String parentId);
}

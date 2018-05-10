package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.Nav;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
     * 通过类型/应用编号/上级编号查询导航数据
     *
     * @param type 类型
     * @param appId 应用编号
     * @param parentId 上级编号
     * @return 导航列表
     */
    List<Nav> findByTypeAndAppIdAndParentId(String type, String appId, String parentId);

    /**
     * 通过类型/应用编号/上级编号/代码查询导航数据
     *
     * @param type 类型
     * @param appId 应用编号
     * @param parentId 上级编号
     * @param code 代码
     * @return 导航列表
     */
    List<Nav> findByTypeAndAppIdAndParentIdAndCode(String type, String appId, String parentId, String code);

    /**
     * 通过上级编号/相邻编号/应用编号查询数据
     *
     * @param parentId 上级编号
     * @param nextId 相邻编号
     * @param appId 应用编号
     * @return 导航对象
     */
    Optional<Nav> findByParentIdAndNextIdAndAppId(String parentId, String nextId, String appId);
}

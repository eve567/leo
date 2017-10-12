package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-10
 * @since 0.1
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    /**
     * 通过编号查询角色
     *
     * @param appId 应用编号
     * @param type 类型
     * @return 角色列表
     */
    List<Role> findByAppIdAndType(String appId, String type);

    /**
     * 通过应用编号查询角色
     *
     * @param appId 应用编号
     * @param type 类型
     * @param status 状态
     * @param pageable 分页信息
     * @return 角色分页信息
     */
    Page<Role> findByAppIdAndTypeAndStatus(String appId, String type, String status, Pageable pageable);
}

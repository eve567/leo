package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.GroupRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 组织角色仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2018-01-02
 * @since 0.1
 */
@Repository
public interface GroupRoleRepository extends JpaRepository<GroupRole, String> {

    /**
     * 通过组织编号查询组织角色关系
     *
     * @param groupId 组织编号
     * @return 组织角色关系列表
     */
    List<GroupRole> findByGroupId(String groupId);

    /**
     * 通过组织编号/应用编号和类型查询组织角色关系
     *
     * @param groupId 组织编号
     * @param appId 应用编号
     * @param type 类型
     * @return 组织角色列表
     */
    @Query("select gr from GroupRole gr where gr.groupId = :groupId and exists (select 1 from Role r where gr.roleId = r.id and r.appId = :appId and r.type = :type)")
    List<GroupRole> findByGroupIdAndAppIdAndType(@Param("groupId") String groupId, @Param("appId") String appId, @Param("type") String type);
}

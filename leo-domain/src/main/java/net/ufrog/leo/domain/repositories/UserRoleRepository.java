package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-28
 * @since 0.1
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {

    /**
     * 通过用户编号查询用户角色关系
     *
     * @param userId 用户编号
     * @return 用户角色关系列表
     */
    List<UserRole> findByUserId(String userId);

    /**
     * 通过用户编号/应用编号和类型查询用户角色关系
     *
     * @param userId 用户编号
     * @param appId 应用编号
     * @param type 类型
     * @return 用户角色关系
     */
    @Query("select ur from UserRole ur where ur.userId = :userId and exists (select 1 from Role r where ur.roleId = r.id and r.appId = :appId and r.type = :type)")
    List<UserRole> findByUserIdAndAppIdAndType(@Param("userId") String userId, @Param("appId") String appId, @Param("type") String type);
}

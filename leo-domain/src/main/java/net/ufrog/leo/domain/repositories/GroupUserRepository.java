package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 组织用户仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-12-01
 * @since 0.1
 */
@Repository
public interface GroupUserRepository extends JpaRepository<GroupUser, String> {

    /**
     * 通过用户编号查询组织用户列表
     *
     * @param userId 用户编号
     * @return 组织用户列表
     */
    List<GroupUser> findByUserId(String userId);

    /**
     * 通过组织编号查询组织用户列表
     *
     * @param groupId 组织编号
     * @return 组织用户列表
     */
    List<GroupUser> findByGroupId(String groupId);
}

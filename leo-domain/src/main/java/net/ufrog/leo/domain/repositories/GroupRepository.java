package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.Group;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 组织仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-11-21
 * @since 0.1
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, String> {

    /**
     * 通过上级编号查询组织列表
     *
     * @param parentId 上级编号
     * @param sort 排序
     * @return 组织列表
     */
    List<Group> findByParentId(String parentId, Sort sort);

    /**
     * 通过上级编号判断组织是否存在
     *
     * @param parentId 上级编号
     * @return 判断结果
     */
    Boolean existsByParentId(String parentId);
}

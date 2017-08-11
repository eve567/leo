package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.RoleResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色资源仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-07
 * @since 0.1
 */
@Repository
public interface RoleResourceRepository extends JpaRepository<RoleResource, String> {

    /**
     * 通过资源编号查询角色资源数据
     *
     * @param resourceId 资源编号
     * @return 角色资源列表
     */
    List<RoleResource> findByResourceId(String resourceId);
}

package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.AppResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 应用资源仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-14
 * @since 0.1
 */
@Repository
public interface AppResourceRepository extends JpaRepository<AppResource, String> {

    /**
     * 通过应用编号查询应用资源
     *
     * @param appId 应用编号
     * @return 应用资源列表
     */
    List<AppResource> findByAppId(String appId);
}

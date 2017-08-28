package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.App;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 应用仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-16
 * @since 0.1
 */
@Repository
public interface AppRepository extends JpaRepository<App, String> {

    /**
     * 通过状态查询应用
     *
     * @param status 状态
     * @param sort 顺序
     * @return 应用编号
     */
    List<App> findByStatus(String status, Sort sort);
}

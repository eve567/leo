package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.UserSignLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户登录日志仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-16
 * @since 0.1
 */
@Repository
public interface UserSignLogRepository extends JpaRepository<UserSignLog, String> {
}

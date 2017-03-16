package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 应用仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-16
 * @since 0.1
 */
@Repository
public interface AppRepository extends JpaRepository<App, String> {
}

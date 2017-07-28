package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 资源仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-04-16
 * @since 0.1
 */
@Repository
public interface ResourceRepository extends JpaRepository<Resource, String> {
}
package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.Blob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 数据仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-11-14
 * @since 0.1
 */
@Repository
public interface BlobRepository extends JpaRepository<Blob, String> {
}

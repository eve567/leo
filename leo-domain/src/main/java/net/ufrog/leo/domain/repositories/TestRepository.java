package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-09
 * @since 0.1
 */
@Repository
public interface TestRepository extends JpaRepository<Test, String> {
}

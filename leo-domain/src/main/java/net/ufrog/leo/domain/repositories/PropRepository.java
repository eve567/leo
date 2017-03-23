package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.Prop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 系统属性仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-23
 * @since 0.1
 */
@Repository
public interface PropRepository extends JpaRepository<Prop, String> {
}

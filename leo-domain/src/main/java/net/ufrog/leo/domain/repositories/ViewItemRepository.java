package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.ViewItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 视图元素仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 5.0.0, 2018-08-13
 * @since 5.0.0
 */
@Repository
public interface ViewItemRepository extends JpaRepository<ViewItem, String> {
}

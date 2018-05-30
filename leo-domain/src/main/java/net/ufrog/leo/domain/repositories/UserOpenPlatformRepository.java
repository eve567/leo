package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.UserOpenPlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户开放平台仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-29
 * @since 0.1
 */
@Repository
public interface UserOpenPlatformRepository extends JpaRepository<UserOpenPlatform, String> {

    /**
     * 通过代码和内容查询用户开放平台
     *
     * @param code 代码
     * @param value 内容
     * @return 用户开放平台
     */
    List<UserOpenPlatform> findByCodeAndValue(String code, String value);
}

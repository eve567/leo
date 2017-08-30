package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.UserOpenPlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
    UserOpenPlatform findByCodeAndValue(String code, String value);
}

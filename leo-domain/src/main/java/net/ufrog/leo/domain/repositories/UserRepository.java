package net.ufrog.leo.domain.repositories;

import net.ufrog.leo.domain.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 用户仓库
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-02-25
 * @since 0.1
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * 通过帐号查询用户
     *
     * @param account 帐号
     * @return 单个用户对象
     */
    User findByAccount(String account);

    /**
     * 通过电子邮件查询用户
     *
     * @param email 电子邮件
     * @return 单个用户对象
     */
    User findByEmail(String email);

    /**
     * 通过手机号码查询用户
     *
     * @param cellphone 电子邮件
     * @return 单个用户对象
     */
    User findByCellphone(String cellphone);

    /**
     * 通过类型集合查询用户
     *
     * @param types 类型集合
     * @param sort 排序
     * @return 用户列表
     */
    List<User> findByTypeIn(Collection<String> types, Sort sort);

    /**
     * 通过类型集合查询用户
     *
     * @param types 类型集合
     * @param pageable 分页信息
     * @return 用户分页对象
     */
    Page<User> findByTypeIn(Collection<String> types, Pageable pageable);
}

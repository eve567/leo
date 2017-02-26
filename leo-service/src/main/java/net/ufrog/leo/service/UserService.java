package net.ufrog.leo.service;

import net.ufrog.leo.domain.models.User;

/**
 * 用户业务接口
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-02-25
 * @since 0.1
 */
public interface UserService {

    /**
     * 通过编号查询用户
     *
     * @param id 用户编号
     * @return 用户对象
     */
    User findById(String id);

    /**
     * 添加用户
     *
     * @param user 用户对象
     * @return 持久化的用户对象
     */
    User add(User user);

    /**
     * 更新用户
     *
     * @param user 用户对象
     * @return 用户对象
     */
    User update(User user);

    /**
     * 重置密码
     *
     * @param id 用户编号
     * @param prev 原密码
     * @param next 新密码
     * @return 用回对象
     */
    User reset(String id, String prev, String next);

    /**
     * 重置密码
     *
     * @param id 用户编号
     * @param next 新密码
     * @return 用回对象
     */
    User reset(String id, String next);

    /**
     * 冻结或解冻<br>
     * 根据不同状态进行处理
     *
     * @param id 用户编号
     * @return 用户对象
     */
    User freezeOrUnfreeze(String id);
}

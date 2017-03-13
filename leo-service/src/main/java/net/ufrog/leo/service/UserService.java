package net.ufrog.leo.service;

import net.ufrog.common.data.spring.Domains;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.leo.domain.models.User;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

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
     * 通过账号查询用户
     *
     * @param account 账号/手机号码/电子邮件
     * @return 用户对象
     */
    User findByAccount(String account);

    /**
     * 认证用户
     *
     * @param account 账号\手机号码\电子邮件
     * @param password 对应密码
     * @param userTypes 用户类型
     * @return 认证的用户对象
     * @exception ServiceException 用户不匹配时抛出异常
     */
    User authenticate(String account, String password, UserType... userTypes) throws ServiceException;

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

    /**
     * 用户类型
     *
     * @author ultrafrog, ufrog.net@gmail.com
     * @version 0.1, 2017-03-13
     * @since 0.1
     */
    enum UserType {
        CLIENT(Domains.sort(Sort.Direction.DESC, "createTime"), User.Type.CLIENT),
        STAFF(Domains.sort(Sort.Direction.DESC, "createTime"), User.Type.STAFF),
        ROOT(Domains.sort(Sort.Direction.DESC, "status", "email"), User.Type.ROOT);

        /** 排序 */
        private Sort sort;

        /** 类型数组 */
        private String[] types;

        /**
         * 构造函数
         *
         * @param sort 排序
         * @param types 类型数组
         */
        UserType(Sort sort, String... types) {
            this.sort = sort;
            this.types = types;
        }

        /**
         * 读取排序
         *
         * @return 排序
         */
        public Sort getSort() {
            return sort;
        }

        /**
         * 读取类型数组
         *
         * @return 类型数组
         */
        public String[] getTypes() {
            return types;
        }

        /**
         * 读取类型列表
         *
         * @return 类型列表
         */
        public List<String> getTypeList() {
            return Arrays.asList(types);
        }
    }
}

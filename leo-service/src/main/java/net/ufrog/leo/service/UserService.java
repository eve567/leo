package net.ufrog.leo.service;

import net.ufrog.common.data.spring.Domains;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.leo.domain.models.User;
import net.ufrog.leo.domain.models.UserOpenPlatform;
import net.ufrog.leo.domain.models.UserRole;
import net.ufrog.leo.domain.models.UserSignLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 用户业务接口
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-02-25
 * @since 0.1
 */
public interface UserService {

    /**
     * 查询所有用户
     *
     * @param types 类型数组
     * @return 用户列表
     */
    List<User> findAll(String... types);

    /**
     * 查询所有用户
     *
     * @param page 当前页码
     * @param size 分页大小
     * @param types 类型数组
     * @return 用户分页信息
     */
    Page<User> findAll(Integer page, Integer size, String... types);

    /**
     * 查询用户角色关系
     *
     * @param appId 应用编号
     * @param userId 用户编号
     * @return 用户角色关系列表
     */
    List<UserRole> findUserRoles(String appId, String userId);

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
     * 创建用户
     *
     * @param user 用户对象
     * @return 持久化的用户对象
     */
    User create(User user);

    /**
     * 更新用户
     *
     * @param user 用户对象
     * @return 用户对象
     */
    User update(User user);

    /**
     * 创建登录日志
     *
     * @param type 类型
     * @param mode 模式
     * @param appId 应用编号
     * @param userId 用户编号
     * @param platformCode 平台代码
     * @param remark 备注
     * @return 用户登录日志
     */
    UserSignLog createSignLog(String type, String mode, String appId, String userId, String platformCode, String remark);

    /**
     * 绑定角色
     *
     * @param userId 用户编号
     * @param appId 应用编号
     * @param roleIds 角色编号数组
     * @return 用户角色列表
     */
    List<UserRole> bindRoles(String userId, String appId, String[] roleIds);

    /**
     * 通过开放平台查询用户
     *
     * @param mCodeValuePair 代码内容对映射表
     * @return 用户 - 安全可选
     */
    Optional<User> findByOpenPlatform(Map<String, String> mCodeValuePair);

    /**
     * 注册开放平台信息<br>该接口不核查重复
     *
     * @param mCodeValuePair 代码内容映射表
     * @param userId 用户编号
     * @return 开放平台模型列表
     */
    List<UserOpenPlatform> registerOpenPlatform(Map<String, String> mCodeValuePair, String userId);

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

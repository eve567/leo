package net.ufrog.leo.domain;

import net.ufrog.common.dict.Dicts;
import net.ufrog.common.utils.Passwords;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.models.RoleResource;
import net.ufrog.leo.domain.models.User;
import net.ufrog.leo.domain.models.UserOpenPlatform;
import net.ufrog.leo.domain.models.UserRole;

/**
 * 模型工具
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-25
 * @since 0.1
 */
public class Models {

    /** 构造函数 */
    private Models() {}

    /**
     * 新建用户角色
     *
     * @param account 账号
     * @param cellphone 手机号码
     * @param email 电子邮件
     * @param name 名称
     * @param password 密码
     * @param status 状态
     * @param type 类型
     * @param forced 是否强制修改密码
     * @return 用户实例
     */
    public static User newUser(String account, String cellphone, String email, String name, String password, String status, String type, String forced) {
        User user = new User();
        user.setAccount(Strings.empty(account, null));
        user.setCellphone(Strings.empty(cellphone, null));
        user.setEmail(Strings.empty(email, null));
        user.setName(Strings.empty(name, null));
        user.setPassword(Strings.empty(password, Passwords.encode(Strings.random(16))));
        user.setStatus(Strings.empty(status, User.Status.ENABLED));
        user.setType(Strings.empty(type, User.Type.CLIENT));
        user.setForced(Strings.empty(forced, Dicts.Bool.FALSE));
        return user;
    }

    /**
     * 创建用户开放平台模型
     *
     * @param code 代码
     * @param value 内容
     * @param userId 用户编号
     * @return 用户开放平台模型
     */
    public static UserOpenPlatform newUserOpenPlatform(String code, String value, String userId) {
        UserOpenPlatform userOpenPlatform = new UserOpenPlatform();
        userOpenPlatform.setCode(code);
        userOpenPlatform.setValue(value);
        userOpenPlatform.setUserId(userId);
        return userOpenPlatform;
    }

    /**
     * 新建角色资源实例
     *
     * @param type 类型
     * @param roleId 角色编号
     * @param resourceId 资源编号
     * @return 角色资源实例
     */
    public static RoleResource newRoleResource(String type, String roleId, String resourceId) {
        RoleResource roleResource = new RoleResource();
        roleResource.setType(type);
        roleResource.setRoleId(roleId);
        roleResource.setResourceId(resourceId);
        return roleResource;
    }

    /**
     * 新建用户角色实例
     *
     * @param userId 用户编号
     * @param roleId 角色编号
     * @return 用户角色实例
     */
    public static UserRole newUserRole(String userId, String roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        return userRole;
    }
}

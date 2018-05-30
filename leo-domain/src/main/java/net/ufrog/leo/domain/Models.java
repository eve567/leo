package net.ufrog.leo.domain;

import net.ufrog.common.dict.Dicts;
import net.ufrog.common.utils.Passwords;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.models.*;

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
     * 新建数据实例
     *
     * @param data 数据内容
     * @return 数据实例
     */
    public static Blob newBlob(byte[] data) {
        Blob blob = new Blob();
        blob.setValue(data);
        return blob;
    }

    /**
     * 新建组织角色实例
     *
     * @param groupId 组织编号
     * @param roleId 角色编号
     * @return 组织角色实例
     */
    public static GroupRole newGroupRole(String groupId, String roleId) {
        GroupRole groupRole = new GroupRole();
        groupRole.setGroupId(groupId);
        groupRole.setRoleId(roleId);
        return groupRole;
    }

    /**
     * 新建组织用户实例
     *
     * @param groupId 组织编号
     * @param userId 用户编号
     * @param remark 备注
     * @return 组织用户实例
     */
    public static GroupUser newGroupUser(String groupId, String userId, String remark) {
        GroupUser groupUser = new GroupUser();
        groupUser.setGroupId(groupId);
        groupUser.setUserId(userId);
        groupUser.setRemark(remark);
        return groupUser;
    }

    /**
     * 新建属性实例
     *
     * @param code 代码
     * @return 属性实例
     */
    public static Prop newProp(String code) {
        Prop prop = new Prop();
        prop.setCode(code);
        return prop;
    }

    /**
     * 新建资源实例
     *
     * @param type 类型
     * @param referenceId 相关编号
     * @return 资源实例
     */
    public static Resource newResource(String type, String referenceId) {
        Resource resource = new Resource();
        resource.setType(type);
        resource.setReferenceId(referenceId);
        return resource;
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
     * @param group 分组
     * @return 用户开放平台模型
     */
    public static UserOpenPlatform newUserOpenPlatform(String code, String value, String userId, String group) {
        UserOpenPlatform userOpenPlatform = new UserOpenPlatform();
        userOpenPlatform.setCode(code);
        userOpenPlatform.setValue(value);
        userOpenPlatform.setUserId(userId);
        userOpenPlatform.setGroup(group);
        return userOpenPlatform;
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

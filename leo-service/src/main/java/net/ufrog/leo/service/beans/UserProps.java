package net.ufrog.leo.service.beans;

import net.ufrog.leo.domain.models.User;
import net.ufrog.leo.domain.models.UserProp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户属性
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-16
 * @since 0.1
 */
public class UserProps implements Serializable {

    private static final long serialVersionUID = 4039620972293085110L;

    /** 用户 */
    private User user;

    /** 用户属性映射 */
    private Map<String, UserProp> mUserProp;

    /** 更新属性映射 */
    private Map<String, Modified> mModified;

    /** 构造函数 */
    private UserProps() {
        this.mUserProp = new HashMap<>();
    }

    /**
     * 构造函数
     *
     * @param user 用户对象
     * @param lUserProp 用户属性对象列表
     */
    public UserProps(User user, List<UserProp> lUserProp) {
        this();
        this.user = user;
        this.convert(lUserProp);
    }

    /**
     * 读取用户对象
     *
     * @return 用户对象
     */
    public User getUser() {
        return user;
    }

    /**
     * 读取用户编号
     *
     * @return 用户编号
     */
    public String getId() {
        return (user == null) ? null : user.getId();
    }

    /**
     * 读取用户帐号
     *
     * @return 用户帐号
     */
    public String getAccount() {
        return (user == null) ? null : user.getAccount();
    }

    /**
     * 读取用户手机号码
     *
     * @return 用户手机号码
     */
    public String getCellphone() {
        return (user == null) ? null : user.getCellphone();
    }

    /**
     * 读取用户电子邮件
     *
     * @return 用户电子邮件
     */
    public String getEmail() {
        return (user == null) ? null : user.getEmail();
    }

    /**
     * 读取用户姓名
     *
     * @return 用户姓名
     */
    public String getName() {
        return (user == null) ? null : user.getName();
    }

    /**
     * 读取用户状态
     *
     * @return 用户状态
     */
    public String getStatus() {
        return (user == null) ? null : user.getStatus();
    }

    /**
     * 读取用户类型
     *
     * @return 用户类型
     */
    public String getType() {
        return (user == null) ? null : user.getType();
    }

    /**
     * 转换属性列表
     *
     * @param lUserProp 用户属性列表
     */
    private void convert(List<UserProp> lUserProp) {
        if (lUserProp != null) {
            lUserProp.forEach(userProp -> mUserProp.put(userProp.getCode(), userProp));
        }
    }

    /**
     * 用户属性
     *
     * @author ultrafrog, ufrog.net@gmail.com
     * @version 0.1, 2017-03-16
     * @since 0.1
     */
    private class Modified implements Serializable {

        private static final long serialVersionUID = -4510044389662964644L;

        /** 原始用户属性 */
        private UserProp userProp;

        /** 修改后的值 */
        private String modified;

        /** 是否锁定原值 */
        private Boolean locked;
    }
}

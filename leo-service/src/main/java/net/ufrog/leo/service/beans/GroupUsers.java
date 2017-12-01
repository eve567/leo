package net.ufrog.leo.service.beans;

import net.ufrog.leo.domain.models.GroupUser;
import net.ufrog.leo.domain.models.User;

import java.io.Serializable;

/**
 * 组织用户对象
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-12-01
 * @since 0.1
 */
public class GroupUsers implements Serializable {

    private static final long serialVersionUID = -2828858068254946357L;

    /** 组织用户 */
    private GroupUser groupUser;

    /** 用户 */
    private User user;

    /** 构造函数 */
    private GroupUsers() {}

    /**
     * 构造函数
     *
     * @param groupUser 组织用户
     * @param user 用户
     */
    public GroupUsers(GroupUser groupUser, User user) {
        this();
        this.groupUser = groupUser;
        this.user = user;
    }

    /**
     * 读取编号
     *
     * @return 编号
     */
    public String getId() {
        return user.getId();
    }

    /**
     * 读取名称
     *
     * @return 名称
     */
    public String getName() {
        return user.getName();
    }

    /**
     * 读取电子邮件
     *
     * @return 电子邮件
     */
    public String getEmail() {
        return user.getEmail();
    }

    /**
     * 读取组织用户
     *
     * @return 组织用户
     */
    public GroupUser getGroupUser() {
        return groupUser;
    }

    /**
     * 设置组织用户
     *
     * @param groupUser 组织用户
     */
    public void setGroupUser(GroupUser groupUser) {
        this.groupUser = groupUser;
    }

    /**
     * 读取用户
     *
     * @return 用户
     */
    public User getUser() {
        return user;
    }

    /**
     * 设置用户
     *
     * @param user 用户
     */
    public void setUser(User user) {
        this.user = user;
    }
}

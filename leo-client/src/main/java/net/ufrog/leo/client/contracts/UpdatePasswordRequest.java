package net.ufrog.leo.client.contracts;

import net.ufrog.aries.common.contract.Request;

/**
 * 更新密码请求
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 4.0.1, 2018-05-16
 * @since 4.0.1
 */
public class UpdatePasswordRequest extends Request {

    private static final long serialVersionUID = 3700702723664511411L;

    /** 新密码 */
    private java.lang.String next;

    /** 原密码 */
    private java.lang.String prev;

    /** 用户编号 */
    private java.lang.String userId;

    /**
     * 读取新密码
     *
     * @return 新密码
     */
    public java.lang.String getNext() {
        return next;
    }

    /**
     * 设置新密码
     *
     * @param next 新密码
     */
    public void setNext(java.lang.String next) {
        this.next = next;
    }

    /**
     * 读取原密码
     *
     * @return 原密码
     */
    public java.lang.String getPrev() {
        return prev;
    }

    /**
     * 设置原密码
     *
     * @param prev 原密码
     */
    public void setPrev(java.lang.String prev) {
        this.prev = prev;
    }

    /**
     * 读取用户编号
     *
     * @return 用户编号
     */
    public java.lang.String getUserId() {
        return userId;
    }

    /**
     * 设置用户编号
     *
     * @param userId 用户编号
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }
}
package net.ufrog.leo.client.contracts;

import net.ufrog.aries.common.contract.Request;

import java.util.HashMap;

/**
 * 开放平台请求
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 4.0.1, 2018-06-04
 * @since 4.0.1
 */
public class OpenPlatformRequest extends Request {

    private static final long serialVersionUID = 2034910521871281195L;

    /** 账号 */
    private java.lang.String account;

    /** 手机号码 */
    private java.lang.String cellphone;

    /** 代码 */
    private java.lang.String code;

    /** 电子邮件 */
    private java.lang.String email;

    /** 名称 */
    private java.lang.String name;

    /** 用户编号 */
    private java.lang.String userId;

    /** 内容 */
    private java.lang.String value;

    /** 代码内容映射表 */
    private java.util.Map<String, String> codeValuePairs;

    /** 构造函数 */
    public OpenPlatformRequest() {
        this.codeValuePairs = new HashMap<>();
    }

    /**
     * 读取账号
     *
     * @return 账号
     */
    public java.lang.String getAccount() {
        return account;
    }

    /**
     * 设置账号
     *
     * @param account 账号
     */
    public void setAccount(java.lang.String account) {
        this.account = account;
    }

    /**
     * 读取手机号码
     *
     * @return 手机号码
     */
    public java.lang.String getCellphone() {
        return cellphone;
    }

    /**
     * 设置手机号码
     *
     * @param cellphone 手机号码
     */
    public void setCellphone(java.lang.String cellphone) {
        this.cellphone = cellphone;
    }

    /**
     * 读取代码
     *
     * @return 代码
     */
    public java.lang.String getCode() {
        return code;
    }

    /**
     * 设置代码
     *
     * @param code 代码
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }

    /**
     * 读取电子邮件
     *
     * @return 电子邮件
     */
    public java.lang.String getEmail() {
        return email;
    }

    /**
     * 设置电子邮件
     *
     * @param email 电子邮件
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    /**
     * 读取名称
     *
     * @return 名称
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(java.lang.String name) {
        this.name = name;
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

    /**
     * 读取内容
     *
     * @return 内容
     */
    public java.lang.String getValue() {
        return value;
    }

    /**
     * 设置内容
     *
     * @param value 内容
     */
    public void setValue(java.lang.String value) {
        this.value = value;
    }

    /**
     * 读取代码内容映射表
     *
     * @return 代码内容映射表
     */
    public java.util.Map<String, String> getCodeValuePairs() {
        return codeValuePairs;
    }

    /**
     * 设置代码内容映射表
     *
     * @param codeValuePairs 代码内容映射表
     */
    public void setCodeValuePairs(java.util.Map<String, String> codeValuePairs) {
        this.codeValuePairs = codeValuePairs;
    }
}
package net.ufrog.leo.client.contracts;

import net.ufrog.aries.common.contract.Request;

import java.util.HashMap;


/**
 * 开放平台请求
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 4.0.1, 2018-05-29
 * @since 4.0.1
 */
public class OpenPlatformRequest extends Request {

    private static final long serialVersionUID = 4716350621491021384L;

    /** 代码内容对 */
    private java.util.Map<String, String> codeValuePairs;

    /** 用户编号 */
    private java.lang.String userId;

    /** 构造函数 */
    public OpenPlatformRequest() {
        codeValuePairs = new HashMap<>();
    }

    /**
     * 读取代码内容对
     *
     * @return 代码内容对
     */
    public java.util.Map<String, String> getCodeValuePairs() {
        return codeValuePairs;
    }

    /**
     * 设置代码内容对
     *
     * @param codeValuePairs 代码内容对
     */
    public void setCodeValuePairs(java.util.Map<String, String> codeValuePairs) {
        this.codeValuePairs = codeValuePairs;
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
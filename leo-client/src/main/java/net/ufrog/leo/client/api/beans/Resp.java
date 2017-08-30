package net.ufrog.leo.client.api.beans;

import net.ufrog.common.Logger;
import net.ufrog.common.dict.Dicts;
import net.ufrog.common.dict.Element;
import net.ufrog.common.utils.Strings;

import java.io.Serializable;

/**
 * 基础响应
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-27
 * @since 0.1
 */
public class Resp implements Serializable {

    private static final long serialVersionUID = -7209823628790400345L;

    /** 响应代码 */
    private String respCode;

    /** 构造函数 */
    public Resp() {
        respCode = RespCode.SUCCESS;
    }

    /**
     * 构造函数
     *
     * @param respCode 响应代码
     */
    public Resp(String respCode) {
        this();
        this.respCode = respCode;
    }

    /**
     * 读取响应代码
     *
     * @return 响应代码
     */
    public String getRespCode() {
        return respCode;
    }

    /**
     * 设置响应代码
     *
     * @param respCode 响应代码
     */
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    /**
     * 读取响应消息
     *
     * @return 响应消息
     */
    public String getRespMsg() {
        String msg = Dicts.name(respCode, null, RespCode.class);
        if (msg == null) {
            Logger.warn("cannot find resp message by code: %s", respCode);
            return Dicts.name(RespCode.UNKNOWN, RespCode.class);
        }
        return msg;
    }

    /**
     * 判断是否成功
     *
     * @return 判断结果
     */
    public Boolean isSuccess() {
        return (Strings.empty(respCode) || Strings.equals(RespCode.SUCCESS, respCode));
    }

    /**
     * 响应基础
     *
     * @author ultrafrog, ufrog.net@gmail.com
     * @version 0.1, 2017-03-27
     * @since 0.1
     */
    public final static class RespCode {

        @Element("成功")
        public final static String SUCCESS = "0000";

        @Element("尚未登录或登录已失效")
        public final static String NOT_SIGN = "0001";

        @Element("授权拒绝")
        public final static String DENIED = "0002";

        @Element("未知异常")
        public final static String UNKNOWN = "9999";
    }
}

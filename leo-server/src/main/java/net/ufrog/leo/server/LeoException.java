package net.ufrog.leo.server;

import net.ufrog.common.exception.ServiceException;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-07-18
 * @since 0.1
 */
public class LeoException extends ServiceException {

    private static final long serialVersionUID = -5640295975261167097L;

    /** 响应代码 */
    private String respCode;

    /** 构造函数 */
    private LeoException() {
        super();
    }

    /**
     * 构造函数
     *
     * @param respCode 响应代码
     */
    public LeoException(String respCode) {
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
}

package net.ufrog.leo.client.contract;

/**
 * 响应代码
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2018-03-29
 * @since 0.1
 */
public class ResultCode {

    @net.ufrog.common.dict.Element("成功")
    public static final String SUCCESS = "0000";

    @net.ufrog.common.dict.Element("网络异常")
    public static final String NETWORK = "9998";

    @net.ufrog.common.dict.Element("未知异常")
    public static final String UNKNOW = "9999";
}

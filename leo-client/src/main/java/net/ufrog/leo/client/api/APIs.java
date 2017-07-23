package net.ufrog.leo.client.api;

import com.alibaba.fastjson.JSON;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import net.ufrog.common.CoreConfig;
import net.ufrog.common.Logger;
import net.ufrog.common.app.App;
import net.ufrog.common.exception.NotSignException;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.client.LeoApp;
import net.ufrog.leo.client.LeoConfig;
import net.ufrog.leo.client.api.beans.AppResp;
import net.ufrog.leo.client.api.beans.AppUserResp;
import net.ufrog.leo.client.api.beans.ListResp;
import net.ufrog.leo.client.api.beans.Resp;

/**
 * 接口工具
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-27
 * @since 0.1
 */
public class APIs {

    private static final String URI_USER = "/api/user/%s/%s";
    private static final String URI_APPS = "/api/apps/%s/%s";

    /** 构造函数 */
    private APIs() {}

    /**
     * 读取当前用户
     *
     * @param token 令牌
     * @return 当前用户
     */
    public static AppUserResp getUser(String token) {
        HttpResponse response = HttpRequest.get(String.format(LeoConfig.getLeoHost() + URI_USER, token, LeoConfig.getLeoAppId())).charset("utf-8").send();
        return parseBytes(response.bodyBytes(), AppUserResp.class);
    }

    /**
     * 查询应用
     *
     * @return 应用列表
     */
    @SuppressWarnings("unchecked")
    public static ListResp<AppResp> findApps() {
        HttpResponse response = HttpRequest.get(String.format(LeoConfig.getLeoHost() + URI_APPS, App.current(LeoApp.class).getAccessToken(), LeoConfig.getLeoAppId())).charset("utf-8").send();
        return parseBytes(response.bodyBytes(), ListResp.class);
    }

    /**
     * 解析字节数组
     *
     * @param bytes 字节数组
     * @param type 类型
     * @param <T> 范型
     * @return 解析对象
     */
    private static <T> T parseBytes(byte[] bytes, Class<T> type) {
        String body = new String(bytes, CoreConfig.getCharset());
        Logger.trace("body str: %s", body);

        if (type != Resp.class) {
            Resp resp = JSON.parseObject(body, Resp.class);
            if (!resp.isSuccess()) {
                if (Strings.equals(Resp.RespCode.NOT_SIGN, resp.getRespCode())) {
                    throw new NotSignException();
                } else {
                    throw new ServiceException(resp.getRespMsg());
                }
            }
        }
        return JSON.parseObject(body, type);
    }
}

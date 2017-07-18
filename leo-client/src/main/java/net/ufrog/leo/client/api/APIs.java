package net.ufrog.leo.client.api;

import com.alibaba.fastjson.JSON;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import net.ufrog.common.CoreConfig;
import net.ufrog.common.Logger;
import net.ufrog.leo.client.LeoConfig;
import net.ufrog.leo.client.api.beans.AppResp;
import net.ufrog.leo.client.api.beans.AppUserResp;

import java.util.List;

/**
 * 接口工具
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-27
 * @since 0.1
 */
public class APIs {

    public static final String URI_USER = "/api/user/%s/%s";

    /** 构造函数 */
    private APIs() {}

    /**
     * 读取当前用户
     *
     * @param token 令牌
     * @param appId 应用编号
     * @return 当前用户
     */
    public static AppUserResp getUser(String token, String appId) {
        HttpResponse response = HttpRequest.get(String.format(LeoConfig.getLeoHost() + URI_USER, token, appId)).charset("utf-8").send();
        return parseBytes(response.bodyBytes(), AppUserResp.class);
    }

    /**
     * 查询应用
     *
     * @return 应用列表
     */
    public static List<AppResp> findApps() {
        return null;
    }

    /**
     * 解析字节数组
     *
     * @param bytes 字节数组
     * @param type 类型
     * @param <T> 范型
     * @return 解析对象
     */
    public static <T> T parseBytes(byte[] bytes, Class<T> type) {
        String body = new String(bytes, CoreConfig.getCharset());
        Logger.trace("body str: %s", body);
        return JSON.parseObject(new String(bytes, CoreConfig.getCharset()), type);
    }
}

package net.ufrog.leo.client.api;

import com.alibaba.fastjson.JSON;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import net.ufrog.common.CoreConfig;
import net.ufrog.common.Logger;
import net.ufrog.common.app.App;
import net.ufrog.common.exception.NotSignException;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Codecs;
import net.ufrog.common.utils.Objects;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.client.LeoApp;
import net.ufrog.leo.client.LeoConfig;
import net.ufrog.leo.client.api.beans.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 接口工具
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-27
 * @since 0.1
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class APIs {

    private static final String URI_USER            = "/api/user/%s/%s";
    private static final String URI_APPS            = "/api/apps/%s/%s";
    private static final String URI_USER_PLATFORM   = "/api/user_open_platform";
    private static final String URI_CHECK_APP       = "/api/check_app";

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
     * 读取当前用户
     *
     * @param token 令牌
     * @return 当前用户
     */
    public static AppUserResp getUser(String token) {
        return getUser(token, LeoConfig.getLeoAppId());
    }

    /**
     * 查询应用
     *
     * @param token 令牌
     * @param appId 应用编号
     * @return 应用列表
     */
    public static ListResp<AppResp> findApps(String token, String appId) {
        HttpResponse response = HttpRequest.get(String.format(LeoConfig.getLeoHost() + URI_APPS, token, appId)).charset("utf-8").send();
        @SuppressWarnings("unchecked") ListResp<AppResp> lrAppResp = parseBytes(response.bodyBytes(), ListResp.class);
        return lrAppResp;
    }

    /**
     * 查询应用
     *
     * @return 应用列表
     */
    public static ListResp<AppResp> findApps() {
        return findApps(App.current(LeoApp.class).getAccessToken(), LeoConfig.getLeoAppId());
    }

    /**
     * 通过开放平台标识授权用户
     *
     * @param appId 应用编号
     * @param email 电子邮件
     * @param cellphone 手机号码
     * @param name 名称
     * @param isMatchAll 是否完全匹配
     * @param isAutoCreate 是否自动创建
     * @param values 内容
     * @return 应用用户响应
     */
    public static AppUserResp getUserByOpenPlatform(String appId, String email, String cellphone, String name, Boolean isMatchAll, Boolean isAutoCreate, String... values) {
        OpenPlatformUserReq opur = new OpenPlatformUserReq();
        opur.setAppId(appId);
        opur.setEmail(email);
        opur.setCellphone(cellphone);
        opur.setName(Strings.empty(name) ? null : Strings.toUnicode(name));
        opur.setIsMatchAll(isMatchAll);
        opur.setIsAutoCreate(isAutoCreate);
        opur.getValues().putAll(Objects.map(String.class, (Object[]) values));

        HttpResponse response = HttpRequest.post(LeoConfig.getLeoHost() + URI_USER_PLATFORM).body(JSON.toJSONString(opur)).charset("utf-8").send();
        return parseBytes(response.bodyBytes(), AppUserResp.class);
    }

    /**
     * 检查应用合法
     *
     * @param appId 应用编号
     * @param appSecret 应用密钥
     * @param timestamp 时间戳
     * @return 检查结果
     */
    public static Resp checkApp(String appId, String appSecret, Long timestamp) {
        Map<String, String> args = sign(appSecret, "appId", appId, "timestamp", String.valueOf(timestamp));
        HttpResponse response = HttpRequest.get(LeoConfig.getLeoHost() + URI_CHECK_APP).query(args).charset("utf-8").send();
        return parseBytes(response.bodyBytes(), Resp.class);
    }

    /**
     * 检查应用合法
     *
     * @return 检查结果
     */
    public static Resp checkApp() {
        return checkApp(LeoConfig.getLeoAppId(), LeoConfig.getLeoAppSecret(), System.currentTimeMillis());
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

    /**
     * 签名
     *
     * @param appSecret 应用密钥
     * @param args 参数
     * @return 参数映射
     */
    public static Map<String, String> sign(String appSecret, String... args) {
        Map<String, String> mArg = Objects.map(String.class, (Object[]) args);
        List<String> lParam = new ArrayList<>(mArg.size());

        mArg.keySet().stream().sorted().forEach(key -> lParam.add(key + "=" + mArg.get(key)));
        String queryStr = Strings.implode(lParam, "&");
        mArg.put("sign", Codecs.md5(queryStr + "&appSecret=" + appSecret));
        Logger.debug("sign query str: %s", queryStr);
        return mArg;
    }

    /**
     * 校验签名
     *
     * @param appSecret 应用密钥
     * @param sign 待校验签名
     * @param args 参数
     * @return 校验结果
     */
    public static Boolean validSign(String appSecret, String sign, String... args) {
        return Strings.equals(sign, sign(appSecret, args).get("sign"));
    }
}

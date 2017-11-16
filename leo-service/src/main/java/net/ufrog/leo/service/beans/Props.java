package net.ufrog.leo.service.beans;

import com.alibaba.fastjson.JSON;
import net.ufrog.common.app.App;
import net.ufrog.common.cache.CacheKey;
import net.ufrog.common.cache.Caches;
import net.ufrog.common.cache.SimpleCacheKey;
import net.ufrog.common.dict.Dicts;
import net.ufrog.common.utils.Strings;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 属性集合
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-13
 * @since 0.1
 */
public class Props {

    public static final String THREAD_POOL_KEY_SCHEDULED    = "scheduled";

    public static final CacheKey CACHE_DEFAULT  = new SimpleCacheKey("default");
    public static final CacheKey CACHE_REDIS    = new SimpleCacheKey("redis");

    /** 应用字符集 */ public static final String APP_CHARSET   = "app.charset";

    //
    static {
        Caches.getImpl(CACHE_DEFAULT);
    }

    /** 构造函数 */
    private Props() {}

    /**
     * 读取应用字符集
     *
     * @return 应用字符集
     */
    public static Charset getAppCharset() {
        return Charset.forName(App.config(APP_CHARSET, "utf-8"));
    }

    /**
     * 转换成十进制小数
     *
     * @param value 值
     * @return 十进制小数
     */
    public static BigDecimal toBigDecimal(String value) {
        return Strings.empty(value) ? null : new BigDecimal(value);
    }

    /**
     * 从十进制小数转换
     *
     * @param value 十进制小数
     * @return 转换后的字符串
     */
    public static String fromBigDecimal(BigDecimal value) {
        return (value == null) ? null : value.toPlainString();
    }

    /**
     * 转换成整型
     *
     * @param value 值
     * @return 整型
     */
    public static Integer toInteger(String value) {
        BigDecimal numeric = toBigDecimal(value);
        return (numeric == null) ? null : numeric.intValue();
    }

    /**
     * 转换成长整型
     *
     * @param value 值
     * @return 长整型
     */
    public static Long toLong(String value) {
        BigDecimal numeric = toBigDecimal(value);
        return (numeric == null) ? null : numeric.longValue();
    }

    /**
     * 转换成浮点型
     *
     * @param value 值
     * @return 浮点型
     */
    public static Float toFloat(String value) {
        BigDecimal numeric = toBigDecimal(value);
        return (numeric == null) ? null : numeric.floatValue();
    }

    /**
     * 转换成双精度浮点型
     *
     * @param value 值
     * @return 双精度浮点型
     */
    public static Double toDouble(String value) {
        BigDecimal numeric = toBigDecimal(value);
        return (numeric == null) ? null : numeric.doubleValue();
    }

    /**
     * 从数字转换
     *
     * @param number 数字
     * @return 转换后的字符串
     */
    public static String fromNumber(Number number) {
        return (number == null) ? null : String.valueOf(number);
    }

    /**
     * 转换成布尔型
     *
     * @param value 值
     * @return 布尔型
     */
    public static Boolean toBoolean(String value) {
        return Strings.equals(Dicts.Bool.TRUE, value);
    }

    /**
     * 从布尔型转换
     *
     * @param value 布尔型
     * @return 转换后的字符串
     */
    public static String fromBoolean(Boolean value) {
        return value ? Dicts.Bool.TRUE : Dicts.Bool.FALSE;
    }

    /**
     * 转换成对象
     *
     * @param value 值
     * @param type 对象类型
     * @param <T> 对象范型
     * @return 对象
     */
    public static <T> T toObject(String value, Class<T> type) {
        return Strings.empty(value) ? null : JSON.parseObject(value, type);
    }

    /**
     * 转换成对象列表
     *
     * @param value 值
     * @param type 对象类型
     * @param <T> 对象范型
     * @return 对象列表
     */
    public static <T> List<T> toObjectList(String value, Class<T> type) {
        return Strings.empty(value) ? null : JSON.parseArray(value, type);
    }

    /**
     * 从对象转换
     *
     * @param value 对象
     * @return 转换后的字符串
     */
    public static String fromObject(Object value) {
        return (value == null) ? null : JSON.toJSONString(value);
    }
}

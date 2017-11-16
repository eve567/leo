package net.ufrog.leo.service.storages;

/**
 * 数据仓储
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-11-14
 * @since 0.1
 */
public interface Storage {

    /**
     * 放置文件
     *
     * @param bytes 字节数组
     * @return 文件仓储标识
     */
    String put(byte[] bytes);

    /**
     * 更新文件
     *
     * @param key 标识
     * @param bytes 字节数组
     */
    void update(String key, byte[] bytes);

    /**
     * 读取文件
     *
     * @param key 标识
     * @return 字节数组
     */
    byte[] get(String key);
}

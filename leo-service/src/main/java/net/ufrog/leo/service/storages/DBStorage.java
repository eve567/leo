package net.ufrog.leo.service.storages;

import net.ufrog.common.Logger;
import net.ufrog.common.cache.Caches;
import net.ufrog.leo.domain.Models;
import net.ufrog.leo.domain.models.Blob;
import net.ufrog.leo.domain.repositories.BlobRepository;

import java.util.Optional;

/**
 * 数据库数据仓储
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-11-14
 * @since 0.1
 */
public class DBStorage implements Storage {

    private static final String PREFIX  = "db:";
    private static final String CACHE   = "blob_";

    /** 数据仓库 */
    private final BlobRepository blobRepository;

    /**
     * 构造函数
     *
     * @param blobRepository 数据仓库
     */
    public DBStorage(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public String put(byte[] bytes) {
        Blob blob = blobRepository.save(Models.newBlob(bytes));
        return PREFIX + blob.getId();
    }

    @Override
    public void update(String key, byte[] bytes) {
        blobRepository.findById(getKey(key)).ifPresent(blob -> {
            blob.setValue(bytes);
            blobRepository.save(blob);
            Caches.delete(CACHE, key);
        });
    }

    @Override
    public byte[] get(String key) {
        return Optional.ofNullable(Caches.get(CACHE, key, byte[].class)).orElseGet(() -> blobRepository.findById(getKey(key)).map(blob -> {
            Caches.set(CACHE, key, blob.getValue());
            return blob.getValue();
        }).orElseGet(() -> {
            Logger.warn("cannot find blob by key: %s", key);
            return new byte[] {};
        }));
    }

    /**
     * 读取标识
     *
     * @param key 原标识
     * @return 处理后的标识
     */
    private String getKey(String key) {
        return key.substring(PREFIX.length());
    }
}

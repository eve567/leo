package net.ufrog.leo.server;

import net.ufrog.common.cache.Caches;
import net.ufrog.common.redis.JedisCacheImpl;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.service.beans.Props;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 缓存服务令牌管理实现<br>jedis impl
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-10-18
 * @since 0.1
 */
public class JedisAccessTokenManager extends AccessTokenManager {

    private static final String CACHE       = "access_token";
    private static final String CACHE_USER  = "access_token_";

    private static JedisCacheImpl cacheImpl = (JedisCacheImpl) Caches.getImpl(Props.CACHE_REDIS).getCache();

    @Override
    public void online(AccessToken accessToken) {
        if (Strings.equals(App.Mulriple.FALSE, accessToken.getApp().getMulriple())) {
            offline(accessToken.getUserId(), accessToken.getAppId(), null);
        } try (Jedis jedis = cacheImpl.getJedis()) {
            jedis.hset(CACHE.getBytes(), accessToken.getToken().getBytes(), cacheImpl.getSerializer().serialize(accessToken));
            jedis.hset((CACHE_USER + accessToken.getUserId()).getBytes(), accessToken.getToken().getBytes(), cacheImpl.getSerializer().serialize(accessToken));
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void offline(String userId, String appId, String token) {
        try (Jedis jedis = cacheImpl.getJedis()) {
            Set<byte[]> sBytes = jedis.hkeys((CACHE_USER + userId).getBytes());
            if (sBytes != null && sBytes.size() > 0) {
                if (Strings.empty(appId)) {
                    jedis.hdel(CACHE.getBytes(), sBytes.toArray(new byte[][] {}));
                    jedis.del((CACHE_USER + userId).getBytes());
                } else if (Strings.empty(token)) {
                    sBytes.stream().filter(bytes -> {
                        AccessToken accessToken = (AccessToken) cacheImpl.getSerializer().deserialize(jedis.hget((CACHE_USER + userId).getBytes(), bytes));
                        return Strings.equals(appId, accessToken.getAppId());
                    }).forEach(bytes -> {
                        jedis.hdel((CACHE_USER + userId).getBytes(), bytes);
                        jedis.hdel(CACHE.getBytes(), bytes);
                    });
                } else {
                    jedis.hdel(CACHE.getBytes(), token.getBytes());
                    jedis.hdel((CACHE_USER + userId).getBytes(), token.getBytes());
                }
            }
        }
    }

    @Override
    public AccessToken get(String token, String appId) {
        try (Jedis jedis = cacheImpl.getJedis()) {
            AccessToken accessToken = (AccessToken) cacheImpl.getSerializer().deserialize(jedis.hget(CACHE.getBytes(), token.getBytes()));
            validate(accessToken, appId);
            return accessToken;
        }
    }

    @Override
    public List<AccessToken> getAll() {
        try (Jedis jedis = cacheImpl.getJedis()) {
            Collection<byte[]> cBytes = jedis.hvals(CACHE.getBytes());
            List<AccessToken> lAccessToken = new ArrayList<>(cBytes.size());

            cBytes.forEach(bytes -> lAccessToken.add((AccessToken) cacheImpl.getSerializer().deserialize(bytes)));
            return lAccessToken;
        }
    }
}

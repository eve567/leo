package net.ufrog.leo.server;

import net.ufrog.common.cache.Caches;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.redis.JedisCacheImpl;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.service.beans.Props;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 缓存服务令牌管理实现
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-10-13
 * @since 0.1
 */
public class RedisAccessTokenManager extends AccessTokenManager {

    private static final String CACHE       = "access_token";
    private static final String CACHE_USER  = "access_token_";

    private static JedisCacheImpl cacheImpl = (JedisCacheImpl) Caches.getImpl(Props.CACHE_REDIS).getCache();

    @Override
    public void online(AccessToken accessToken) {
        if (Strings.equals(App.Mulriple.FALSE, accessToken.getApp().getMulriple())) {
            offline(accessToken.getUserId(), accessToken.getAppId(), null);
        } try (JedisCluster jedisCluster = cacheImpl.getJedisCluster()) {
            jedisCluster.hset(CACHE.getBytes(), accessToken.getToken().getBytes(), cacheImpl.getSerializer().serialize(accessToken));
            jedisCluster.hset((CACHE_USER + accessToken.getUserId()).getBytes(), accessToken.getToken().getBytes(), cacheImpl.getSerializer().serialize(accessToken));
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void offline(String userId, String appId, String token) {
        try (JedisCluster jedisCluster = cacheImpl.getJedisCluster()) {
            Set<byte[]> sBytes = jedisCluster.hkeys((CACHE_USER + userId).getBytes());
            if (sBytes != null && sBytes.size() > 0) {
                if (Strings.empty(appId)) {
                    jedisCluster.hdel(CACHE.getBytes(), sBytes.toArray(new byte[][] {}));
                    jedisCluster.del((CACHE_USER + userId).getBytes());
                } else if (Strings.empty(token)) {
                    sBytes.stream().filter(bytes -> {
                        AccessToken accessToken = (AccessToken) cacheImpl.getSerializer().deserialize(jedisCluster.hget((CACHE_USER + userId).getBytes(), bytes));
                        return Strings.equals(appId, accessToken.getAppId());
                    }).forEach(bytes -> {
                        jedisCluster.hdel((CACHE_USER + userId).getBytes(), bytes);
                        jedisCluster.hdel(CACHE.getBytes(), bytes);
                    });
                } else {
                    jedisCluster.hdel(CACHE.getBytes(), token.getBytes());
                    jedisCluster.hdel((CACHE_USER + userId).getBytes(), token.getBytes());
                }
            }
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public AccessToken get(String token, String appId) {
        try (JedisCluster jedisCluster = cacheImpl.getJedisCluster()) {
            AccessToken accessToken = (AccessToken) cacheImpl.getSerializer().deserialize(jedisCluster.hget(CACHE.getBytes(), token.getBytes()));
            validate(accessToken, appId);
            return accessToken;
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<AccessToken> getAll() {
        try (JedisCluster jedisCluster = cacheImpl.getJedisCluster()) {
            Collection<byte[]> cBytes = jedisCluster.hvals(CACHE.getBytes());
            List<AccessToken> lAccessToken = new ArrayList<>(cBytes.size());

            cBytes.forEach(bytes -> lAccessToken.add((AccessToken) cacheImpl.getSerializer().deserialize(bytes)));
            return lAccessToken;
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}

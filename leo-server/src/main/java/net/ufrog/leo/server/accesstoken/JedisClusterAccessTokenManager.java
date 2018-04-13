package net.ufrog.leo.server.accesstoken;

import net.ufrog.common.Logger;
import net.ufrog.common.cache.Caches;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.redis.JedisCacheImpl;
import net.ufrog.common.redis.JedisClusterCacheImpl;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.models.App;
import net.ufrog.leo.service.beans.Props;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * 缓存服务令牌管理实现
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-12
 * @since 3.0.0
 */
@SuppressWarnings("unused")
public class JedisClusterAccessTokenManager extends AccessTokenManager {

    private static final String CACHE       = "access_token";
    private static final String CACHE_USER  = "access_token_";

    private static JedisClusterCacheImpl cacheImpl = (JedisClusterCacheImpl) Caches.getImpl(Props.CACHE_REDIS).getCache();

    @Override
    public void online(AccessToken accessToken) {
        if (Strings.equals(App.Mulriple.FALSE, accessToken.getApp().getMulriple())) {
            offline(accessToken.getUserId(), accessToken.getAppId(), null);
            Logger.debug("kick out user '%s' from app '%s'", accessToken.getUserId(), accessToken.getAppId());
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
        return null;
    }

    @Override
    public List<AccessToken> getAll() {
        return null;
    }
}

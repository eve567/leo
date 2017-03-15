package net.ufrog.leo.server.beans.access.token;

import net.ufrog.common.Logger;
import net.ufrog.common.utils.Calendars;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.service.beans.Props;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 开发访问令牌管理实现
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-14
 * @since 0.1
 */
public class DevAccessTokenManager implements AccessTokenManager {

    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    private static Map<String, AccessToken> mAccessToken = new ConcurrentHashMap<>();
    private static Map<String, List<String>> mUserToken = new ConcurrentHashMap<>();

    @Override
    public void online(AccessToken accessToken) {
        String id = accessToken.getAppUser().getId();
        String token = accessToken.getToken();

        if (Props.getLeoSignKickout()) {
            offline(id, null);
        } if (!mUserToken.containsKey(id)) {
            mUserToken.put(id, Collections.synchronizedList(new ArrayList<>()));
        }

        mUserToken.get(id).add(token);
        mAccessToken.put(token, accessToken);
        scheduledExecutorService.schedule(() -> {
            offline(id, token);
            Logger.info("offline user: %s by token: %s, %s", id, token, Calendars.datetime());
        }, AccessToken.getTimeout(), TimeUnit.SECONDS);
    }

    @Override
    public AccessToken online(String id, String account, String name) {
        AccessToken accessToken = new AccessToken(id, account, name);
        online(accessToken);
        return accessToken;
    }

    @Override
    public void offline(String id, String token) {
        if (mUserToken.containsKey(id)) {
            List<String> lToken = mUserToken.get(id);
            if (Strings.empty(token)) {
                lToken.stream().filter(mAccessToken::containsKey).forEach(mAccessToken::remove);
                lToken.clear();
            } else {
                if (mAccessToken.containsKey(token)) mAccessToken.remove(token);
                if (lToken.contains(token)) lToken.remove(token);
            }
        }
    }
}

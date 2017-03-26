package net.ufrog.leo.server;

import net.ufrog.common.app.AppUser;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.domain.models.App;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 开发访问令牌管理实现
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-14
 * @since 0.1
 */
public class DevAccessTokenManager implements AccessTokenManager {

    private static Map<String, AccessToken> mAccessToken = new ConcurrentHashMap<>();
    private static Map<String, List<AccessToken>> mUserAccessToken = new ConcurrentHashMap<>();

    @Override
    public void online(AccessToken accessToken) {
        if (Strings.equals(App.Mulriple.FALSE, accessToken.getApp().getMulriple())) offline(accessToken.getUserId(), accessToken.getAppId(), null);
        if (!mUserAccessToken.containsKey(accessToken.getUserId())) mUserAccessToken.put(accessToken.getUserId(), Collections.synchronizedList(new ArrayList<>()));

        mAccessToken.put(accessToken.getToken(), accessToken);
        mUserAccessToken.get(accessToken.getUserId()).add(accessToken);
    }

    @Override
    public AccessToken online(AppUser appUser, App app, String ip) {
        AccessToken accessToken = new AccessToken(appUser, app, ip);
        online(accessToken);
        return accessToken;
    }

    @Override
    public void offline(String userId, String appId, String token) {
        if (mUserAccessToken.containsKey(userId)) {
            List<AccessToken> lAccessToken = mUserAccessToken.get(userId);
            if (Strings.empty(appId)) {
                lAccessToken.stream().filter(accessToken -> mAccessToken.containsKey(accessToken.getToken())).forEach(accessToken -> mAccessToken.remove(accessToken.getToken()));
                lAccessToken.clear();
            } else if (Strings.empty(token)) {
                List<AccessToken> lRemove = lAccessToken.stream().filter(accessToken -> Strings.equals(accessToken.getAppId(), appId)).collect(Collectors.toList());
                lRemove.stream().filter(accessToken -> mAccessToken.containsKey(accessToken.getToken())).forEach(accessToken -> mAccessToken.remove(accessToken.getToken()));
                lAccessToken.removeAll(lRemove);
            } else {
                if (mAccessToken.containsKey(token)) mAccessToken.remove(token);
                List<AccessToken> lRemove = lAccessToken.stream().filter(accessToken -> (Strings.equals(accessToken.getToken(), token) && Strings.equals(accessToken.getAppId(), appId))).collect(Collectors.toList());
                lAccessToken.removeAll(lRemove);
            }
        }
    }

    @Override
    public AccessToken get(String token) {
        return mAccessToken.get(token);
    }
}

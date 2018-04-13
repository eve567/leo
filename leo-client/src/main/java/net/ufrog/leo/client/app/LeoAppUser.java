package net.ufrog.leo.client.app;

import net.ufrog.common.app.AppUser;

/**
 * 用户中心应用用户
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-11
 * @since 3.0.0
 */
public class LeoAppUser extends AppUser {

    private static final long serialVersionUID = -1991890287339861205L;

    /** 令牌 */
    private String token;

    /**
     * 构造函数
     *
     * @param id 编号
     * @param account 账号
     * @param name 名称
     */
    public LeoAppUser(String id, String account, String name) {
        super(id, account, name);
    }

    /**
     * 读取令牌
     *
     * @return 令牌
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置令牌
     *
     * @param token 令牌
     */
    public void setToken(String token) {
        this.token = token;
    }
}

package net.ufrog.leo.client;

import net.ufrog.common.app.AppUser;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-13
 * @since 0.1
 */
public class LeoAppUser extends AppUser {

    private static final long serialVersionUID = -8577812680514415491L;

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

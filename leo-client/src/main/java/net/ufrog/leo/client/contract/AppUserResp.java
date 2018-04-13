package net.ufrog.leo.client.contract;

import net.ufrog.aries.common.contract.Resp;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-04-11
 * @since 3.0.0
 */
public class AppUserResp extends Resp {

    private static final long serialVersionUID = 5154888543171961093L;

    /** 编号 */
    private String id;

    /** 账号 */
    private String account;

    /** 名称 */
    private String name;

    /** 令牌 */
    private String token;

    /**
     * 读取编号
     *
     * @return 编号
     */
    public String getId() {
        return id;
    }

    /**
     * 设置编号
     *
     * @param id 编号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 读取账号
     *
     * @return 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账号
     *
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 读取名称
     *
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
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

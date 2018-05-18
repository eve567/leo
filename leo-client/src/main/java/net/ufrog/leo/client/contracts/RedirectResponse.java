package net.ufrog.leo.client.contracts;

/**
 * 跳转响应
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 4.0.1, 2018-05-16
 * @since 4.0.1
 */
public class RedirectResponse extends AccessTokenResponse {

    private static final long serialVersionUID = -7762641916738493586L;

    /** 跳转地址 */
    private String url;

    /**
     * 读取跳转地址
     *
     * @return 跳转地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置跳转地址
     *
     * @param url 跳转地址
     */
    public void setUrl(String url) {
        this.url = url;
    }
}

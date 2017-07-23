package net.ufrog.leo.client.api.beans;

/**
 * 功能接口响应
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-07-21
 * @since 0.1
 */
public class NavResp extends Resp {

    private static final long serialVersionUID = -3802692455262925789L;

    /** 编号 */
    private String id;

    /** 名称 */
    private String name;

    /** 副称 */
    private String subname;

    /** 代码 */
    private String code;

    /** 访问地址 */
    private String url;

    /** 目标 */
    private String target;

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
     * 读取副称
     *
     * @return 副称
     */
    public String getSubname() {
        return subname;
    }

    /**
     * 设置副称
     *
     * @param subname 副称
     */
    public void setSubname(String subname) {
        this.subname = subname;
    }

    /**
     * 读取代码
     *
     * @return 代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置代码
     *
     * @param code 代码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 读取访问地址
     *
     * @return 访问地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置访问地址
     *
     * @param url 访问地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 读取目标
     *
     * @return 目标
     */
    public String getTarget() {
        return target;
    }

    /**
     * 设置目标
     *
     * @param target 目标
     */
    public void setTarget(String target) {
        this.target = target;
    }
}

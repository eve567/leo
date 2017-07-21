package net.ufrog.leo.client.api.beans;

/**
 * 应用接口响应
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-27
 * @since 0.1
 */
public class AppResp extends Resp {

    private static final long serialVersionUID = 6622423916544688001L;

    /** 编号 */
    private String id;

    /** 名称 */
    private String name;

    /** 代码 */
    private String code;

    /** 图标 */
    private String logo;

    /** 色值 */
    private String color;

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
     * 读取图标
     *
     * @return 图标
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 设置图标
     *
     * @param logo 图标
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * 读取色值
     *
     * @return 色值
     */
    public String getColor() {
        return color;
    }

    /**
     * 设置色值
     *
     * @param color 色值
     */
    public void setColor(String color) {
        this.color = color;
    }
}

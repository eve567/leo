package net.ufrog.leo.client.contracts;

import net.ufrog.aries.common.contract.Response;

/**
 * 视图元素响应
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 5.0.0, 2018-08-22
 * @since 5.0.0
 */
public class ViewItemResponse extends Response {

    private static final long serialVersionUID = -6034390908106804192L;

    /** 编号 */
    private String id;

    /** 名称 */
    private String name;

    /** 代码 */
    private String code;

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
}

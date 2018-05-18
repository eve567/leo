package net.ufrog.leo.client.contracts;

import net.ufrog.aries.common.contract.Response;

/**
 * 属性响应
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 3.0.0, 2018-05-15
 * @since 3.0.0
 */
public class PropResponse extends Response {

    private static final long serialVersionUID = 7660554371405917882L;

    /** 编号 */
    private String id;

    /** 代码 */
    private String code;

    /** 内容 */
    private String value;

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
     * 读取内容
     *
     * @return 内容
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置内容
     *
     * @param value 内容
     */
    public void setValue(String value) {
        this.value = value;
    }
}

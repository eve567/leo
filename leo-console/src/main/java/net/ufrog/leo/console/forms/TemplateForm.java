package net.ufrog.leo.console.forms;

import java.io.Serializable;

/**
 * 模版表单
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-11-15
 * @since 0.1
 */
public class TemplateForm implements Serializable {

    private static final long serialVersionUID = 8257304804096210383L;

    /** 代码 */
    private String code;

    /** 内容 */
    private String value;

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

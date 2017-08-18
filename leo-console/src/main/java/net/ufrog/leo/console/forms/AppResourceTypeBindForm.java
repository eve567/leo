package net.ufrog.leo.console.forms;

import java.io.Serializable;

/**
 * 应用资源类型绑定表单
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-15
 * @since 0.1
 */
public class AppResourceTypeBindForm implements Serializable {

    private static final long serialVersionUID = 7316414997957574332L;

    /** 资源类型数组 */
    private String[] resourceTypes;

    /**
     * 读取资源类型数组
     *
     * @return 资源类型数组
     */
    public String[] getResourceTypes() {
        return resourceTypes;
    }

    /**
     * 设置资源类型数组
     *
     * @param resourceTypes 资源类型数组
     */
    public void setResourceTypes(String[] resourceTypes) {
        this.resourceTypes = resourceTypes;
    }
}

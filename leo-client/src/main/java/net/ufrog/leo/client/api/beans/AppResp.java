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

    /** 设置 */
    private String color;

    /** 图标 */
    private String logo;
}

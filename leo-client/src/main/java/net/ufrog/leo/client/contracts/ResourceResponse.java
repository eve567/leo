package net.ufrog.leo.client.contracts;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.ufrog.aries.common.contract.Response;

/**
 * 资源响应
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 5.0.0, 2018-07-09
 * @since 5.0.0
 */
@ApiModel(value = "资源响应")
public class ResourceResponse extends Response {

    private static final long serialVersionUID = -2823707095660368130L;

    /** 编号 */
    @ApiModelProperty(value = "资源编号")
    private String id;

    /** 类型 */
    @ApiModelProperty(value = "资源类型")
    private String type;

    /** 相关编号 */
    @ApiModelProperty(value = "相关编号")
    private String referenceId;

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
     * 读取类型
     *
     * @return 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 读取相关编号
     *
     * @return 相关编号
     */
    public String getReferenceId() {
        return referenceId;
    }

    /**
     * 设置相关编号
     *
     * @param referenceId 相关编号
     */
    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}

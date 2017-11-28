package net.ufrog.leo.domain.models;

/**
 * 组织模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-11-21
 * @since 0.1
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "leo_group")
public class Group extends Model {

    private static final long serialVersionUID = -8112194330217466145L;

    public static final String PARENT_ID_ROOT  = "_root";

    /** 名称 */
    @javax.persistence.Column(name = "vc_name")
    private java.lang.String name;

    /** 代码 */
    @javax.persistence.Column(name = "vc_code")
    private java.lang.String code;

    /** 上级编号 */
    @javax.persistence.Column(name = "fk_parent_id")
    private java.lang.String parentId;

    /**
     * 读取名称
     *
     * @return 名称
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    /**
     * 读取代码
     *
     * @return 代码
     */
    public java.lang.String getCode() {
        return code;
    }

    /**
     * 设置代码
     *
     * @param code 代码
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }

    /**
     * 读取上级编号
     *
     * @return 上级编号
     */
    public java.lang.String getParentId() {
        return parentId;
    }

    /**
     * 设置上级编号
     *
     * @param parentId 上级编号
     */
    public void setParentId(java.lang.String parentId) {
        this.parentId = parentId;
    }
}
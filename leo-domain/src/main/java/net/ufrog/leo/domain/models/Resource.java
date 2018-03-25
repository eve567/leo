package net.ufrog.leo.domain.models;

import net.ufrog.aries.common.jpa.Model;

/**
 * 资源模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-04-17
 * @since 0.1
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "leo_resource")
public class Resource extends Model {

    private static final long serialVersionUID = -1342053718899674755L;

    /** 类型 */
    @javax.persistence.Column(name = "dc_type")
    private java.lang.String type;

    /** 相关编号 */
    @javax.persistence.Column(name = "fk_reference_id")
    private java.lang.String referenceId;

    /**
     * 读取类型
     *
     * @return 类型
     */
    public java.lang.String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }

    /**
     * 读取相关编号
     *
     * @return 相关编号
     */
    public java.lang.String getReferenceId() {
        return referenceId;
    }

    /**
     * 设置相关编号
     *
     * @param referenceId 相关编号
     */
    public void setReferenceId(java.lang.String referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * 类型
     *
     * @author ultrafrog
     * @version 0.1, 2017-04-17
     * @since 0.1
     */
    public static final class Type {

        @net.ufrog.common.dict.Element(value = "导航", code = "net.ufrog.leo.domain.models.Nav")
        public static final String NAV = "00";

        @net.ufrog.common.dict.Element(value = "应用", code = "net.ufrog.leo.domain.models.App")
        public static final String APP = "99";
    }
}
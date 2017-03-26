package net.ufrog.leo.domain.models;

import net.ufrog.common.Link;

/**
 * 导航模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-24
 * @since 0.1
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "leo_nav")
public class Nav extends Model implements Link<String> {

    private static final long serialVersionUID = -5945791295949876544L;

    /** 名称 */
    @javax.persistence.Column(name = "vc_name")
    private java.lang.String name;

    /** 副称 */
    @javax.persistence.Column(name = "vc_subname")
    private java.lang.String subname;

    /** 代码 */
    @javax.persistence.Column(name = "vc_code")
    private java.lang.String code;

    /** 路径 */
    @javax.persistence.Column(name = "vc_path")
    private java.lang.String path;

    /** 目标 */
    @javax.persistence.Column(name = "vc_target")
    private java.lang.String target;

    /** 类型 */
    @javax.persistence.Column(name = "dc_type")
    private java.lang.String type;

    /** 上级编号 */
    @javax.persistence.Column(name = "fk_parent_id")
    private java.lang.String parentId;

    /** 下位编号 */
    @javax.persistence.Column(name = "fk_next_id")
    private java.lang.String nextId;

    /** 应用编号 */
    @javax.persistence.Column(name = "fk_app_id")
    private java.lang.String appId;

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
     * 读取副称
     *
     * @return 副称
     */
    public java.lang.String getSubname() {
        return subname;
    }

    /**
     * 设置副称
     *
     * @param subname 副称
     */
    public void setSubname(java.lang.String subname) {
        this.subname = subname;
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
     * 读取路径
     *
     * @return 路径
     */
    public java.lang.String getPath() {
        return path;
    }

    /**
     * 设置路径
     *
     * @param path 路径
     */
    public void setPath(java.lang.String path) {
        this.path = path;
    }

    /**
     * 读取目标
     *
     * @return 目标
     */
    public java.lang.String getTarget() {
        return target;
    }

    /**
     * 设置目标
     *
     * @param target 目标
     */
    public void setTarget(java.lang.String target) {
        this.target = target;
    }

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

    /**
     * 读取下位编号
     *
     * @return 下位编号
     */
    public java.lang.String getNextId() {
        return nextId;
    }

    /**
     * 设置下位编号
     *
     * @param nextId 下位编号
     */
    public void setNextId(java.lang.String nextId) {
        this.nextId = nextId;
    }

    /**
     * 读取应用编号
     *
     * @return 应用编号
     */
    public java.lang.String getAppId() {
        return appId;
    }

    /**
     * 设置应用编号
     *
     * @param appId 应用编号
     */
    public void setAppId(java.lang.String appId) {
        this.appId = appId;
    }

    /**
     * 类型
     *
     * @author ultrafrog
     * @version 0.1, 2017-03-24
     * @since 0.1
     */
    public static final class Type {

        @net.ufrog.common.dict.Element("控制台")
        public static final String CONSOLE = "99";
    }
}
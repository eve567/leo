package net.ufrog.leo.service.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出功能
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-10-09
 * @since 0.1
 */
public class ExportNav implements Serializable {

    private static final long serialVersionUID = -6658866412826486514L;

    /** 代码 */
    private String code;

    /** 名称 */
    private String name;

    /** 路径 */
    private String path;

    /** 副称 */
    private String subname;

    /** 目标 */
    private String target;

    /** 下级列表 */
    private List<ExportNav> children;

    /** 构造函数 */
    public ExportNav() {
        this.children = new ArrayList<>();
    }

    /**
     * 构造函数
     *
     * @param code 代码
     * @param name 名称
     * @param path 路径
     * @param subname 副称
     * @param target 目标
     */
    public ExportNav(String code, String name, String path, String subname, String target) {
        this();
        this.code = code;
        this.name = name;
        this.path = path;
        this.subname = subname;
        this.target = target;
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
     * 读取路径
     *
     * @return 路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置路径
     *
     * @param path 路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 读取副称
     *
     * @return 副称
     */
    public String getSubname() {
        return subname;
    }

    /**
     * 设置副称
     *
     * @param subname 副称
     */
    public void setSubname(String subname) {
        this.subname = subname;
    }

    /**
     * 读取目标
     *
     * @return 目标
     */
    public String getTarget() {
        return target;
    }

    /**
     * 设置目标
     *
     * @param target 目标
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * 读取下级列表
     *
     * @return 下级列表
     */
    public List<ExportNav> getChildren() {
        return children;
    }
}
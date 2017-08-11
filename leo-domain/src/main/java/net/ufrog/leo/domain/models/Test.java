package net.ufrog.leo.domain.models;

/**
 * 测试模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-09
 * @since 0.1
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "leo_test")
public class Test extends Model {

    private static final long serialVersionUID = 2607321391896763409L;

    /** 名称 */
    @javax.persistence.Column(name = "vc_name")
    private java.lang.String name;

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
}
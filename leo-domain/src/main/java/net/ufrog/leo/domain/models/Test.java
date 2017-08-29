package net.ufrog.leo.domain.models;

/**
 * 测试模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-29
 * @since 0.1
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "leo_test")
public class Test extends Model {

    private static final long serialVersionUID = 6510797804548215338L;

    /** 名称 */
    @javax.persistence.Column(name = "vc_name")
    private java.lang.String name;

    /** 数字 */
    @javax.persistence.Column(name = "nb_num")
    private java.lang.Integer num;

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
     * 读取数字
     *
     * @return 数字
     */
    public java.lang.Integer getNum() {
        return num;
    }

    /**
     * 设置数字
     *
     * @param num 数字
     */
    public void setNum(java.lang.Integer num) {
        this.num = num;
    }
}
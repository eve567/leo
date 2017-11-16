package net.ufrog.leo.domain.models;

/**
 * 数据模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-11-13
 * @since 0.1
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "leo_blob")
public class Blob extends Model {

    private static final long serialVersionUID = -5461983399277921196L;

    /** 内容 */
    @javax.persistence.Lob
    @javax.persistence.Column(name = "bb_value")
    private byte[] value;

    /**
     * 读取内容
     *
     * @return 内容
     */
    public byte[] getValue() {
        return value;
    }

    /**
     * 设置内容
     *
     * @param value 内容
     */
    public void setValue(byte[] value) {
        this.value = value;
    }
}
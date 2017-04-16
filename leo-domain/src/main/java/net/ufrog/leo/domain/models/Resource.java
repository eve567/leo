package net.ufrog.leo.domain.models;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 资源模型
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-04-04
 * @since 0.1
 */
@Entity
@Table(name = "leo_resource")
public class Resource extends Model {

    private static final long serialVersionUID = 1113684008838460096L;

    /**
     * 读取相关编号
     *
     * @return
     */
    public String getReferenceId() {
        return null;
    }
}

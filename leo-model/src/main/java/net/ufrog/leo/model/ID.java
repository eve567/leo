package net.ufrog.leo.model;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 编号
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-02-18
 * @since 0.1
 */
@MappedSuperclass
public class ID implements Serializable {

    private static final long serialVersionUID = -6098778966232582996L;

    /** 编号 */
    private String id;
}

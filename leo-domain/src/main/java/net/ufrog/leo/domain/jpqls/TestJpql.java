package net.ufrog.leo.domain.jpqls;

import net.ufrog.common.data.hibernate.HibernateJpql;
import org.springframework.stereotype.Component;

/**
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-29
 * @since 0.1
 */
@Component
public class TestJpql extends HibernateJpql {

    public Integer updateById(String id, Integer amt) {
        String sql = "update leo_test t set t.nb_num = t.nb_num + :amt where t.pk_id = :id";
        return executeNative(sql, "amt", amt, "id", id);
    }
}

package net.ufrog.leo.domain.mappers;

import net.ufrog.leo.domain.models.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * 测试用暂时无用
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-11
 * @since 0.1
 */
@Component
public interface UserMapper {

    @Select("select User.pk_id id, User.vc_name name from leo_user User where User.pk_id = #{id}")
    User findOne(@Param("id") String id);
}

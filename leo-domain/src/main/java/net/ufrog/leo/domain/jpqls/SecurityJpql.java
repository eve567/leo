package net.ufrog.leo.domain.jpqls;

import net.ufrog.common.data.QueryScript;
import net.ufrog.common.data.hibernate.HibernateJpql;
import net.ufrog.leo.domain.models.Role;
import net.ufrog.leo.domain.models.RoleResource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 权限脚本
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-04-04
 * @since 0.1
 */
@Component
public class SecurityJpql extends HibernateJpql {

    /**
     * 通过角色编号和类型查询角色资源关系
     *
     * @param roleId 角色编号
     * @param type 类型
     * @return 资源列表
     */
    public List<RoleResource> findRoleResourceByRoleIdAndType(String roleId, String type) {
        QueryScript qs = new QueryScript("from RoleResource rr where 1 = 1");
        qs.and("rr.roleId = :roleId", Boolean.TRUE, "roleId", roleId);
        qs.and("exists (select 1 from Resource r where r.id = rr.resourceId and r.type = :type)", Boolean.TRUE, "type", type);
        return find(qs.getScript(), qs.getArguments(), RoleResource.class);
    }
}

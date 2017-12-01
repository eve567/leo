package net.ufrog.leo.service;

import net.ufrog.leo.domain.models.Group;
import net.ufrog.leo.service.beans.GroupUsers;

import java.util.List;

/**
 * 组织业务接口
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-11-21
 * @since 0.1
 */
public interface GroupService {

    /**
     * 通过上级编号查询组织列表
     *
     * @param parentId 上级编号
     * @return 组织列表
     */
    List<Group> findByParentId(String parentId);

    /**
     * 通过组织编号查询组织用户关系
     *
     * @param groupId 组织编号
     * @return 组织用户关系列表
     */
    List<GroupUsers> findGroupUsersByGroupId(String groupId);

    /**
     * 创建组织
     *
     * @param group 组织对象
     * @return 持久化组织对象
     */
    Group create(Group group);
}

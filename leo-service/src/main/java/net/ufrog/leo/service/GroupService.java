package net.ufrog.leo.service;

import net.ufrog.leo.domain.models.Group;
import net.ufrog.leo.domain.models.GroupRole;
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
     * 通过组织编号查询组织角色关系
     *
     * @param groupId 组织编号
     * @param appId 应用编号
     * @return 组织角色关系列表
     */
    List<GroupRole> findGroupRoleByGroupId(String groupId, String appId);

    /**
     * 创建组织
     *
     * @param group 组织对象
     * @return 持久化组织对象
     */
    Group create(Group group);

    /**
     * 更新组织
     *
     * @param groupId 组织编号
     * @param group 组织对象
     * @return 持久化组织对象
     */
    Group update(String groupId, Group group);

    /**
     * 删除组织
     *
     * @param groupId 组织编号
     * @return 被删除组织对象
     */
    Group delete(String groupId);

    /**
     * 创建组织用户关系
     *
     * @param groupId 组织编号
     * @param userIds 用户编号数组
     * @return 组织用户关系列表
     */
    List<GroupUsers> createGroupUsers(String groupId, String[] userIds);

    /**
     * 删除组织用户关系
     *
     * @param groupUserId 组织用户编号
     * @return 被删除组织用户关系
     */
    GroupUsers deleteGroupUser(String groupUserId);

    /**
     * 备注组织用户关系
     *
     * @param groupUserId 组织用户编号
     * @param remark 备注
     * @return 备注后的组织用户关系
     */
    GroupUsers remarkGroupUser(String groupUserId, String remark);

    /**
     * 绑定角色
     *
     * @param groupId 组织编号
     * @param appId 应用编号
     * @param roleIds 角色编号数组
     * @return 已绑定组织角色关系列表
     */
    List<GroupRole> bindRoles(String groupId, String appId, String[] roleIds);
}

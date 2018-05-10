package net.ufrog.leo.service.impls;

import net.ufrog.common.Logger;
import net.ufrog.common.data.spring.Domains;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Objects;
import net.ufrog.leo.domain.Models;
import net.ufrog.leo.domain.models.Group;
import net.ufrog.leo.domain.models.GroupRole;
import net.ufrog.leo.domain.models.GroupUser;
import net.ufrog.leo.domain.models.Role;
import net.ufrog.leo.domain.repositories.GroupRepository;
import net.ufrog.leo.domain.repositories.GroupRoleRepository;
import net.ufrog.leo.domain.repositories.GroupUserRepository;
import net.ufrog.leo.domain.repositories.UserRepository;
import net.ufrog.leo.service.GroupService;
import net.ufrog.leo.service.beans.GroupUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * 组织业务实现
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-11-21
 * @since 0.1
 */
@Service
@Transactional(readOnly = true)
public class GroupServiceImpl implements GroupService {

    /** 组织仓库 */
    private final GroupRepository groupRepository;

    /** 组织角色仓库 */
    private final GroupRoleRepository groupRoleRepository;

    /** 组织用户仓库 */
    private final GroupUserRepository groupUserRepository;

    /** 用户仓库 */
    private final UserRepository userRepository;

    /**
     * 构造函数
     *
     * @param groupRepository 组织仓库
     * @param groupRoleRepository 组织角色仓库
     * @param groupUserRepository 组织用户仓库
     * @param userRepository 用户仓库
     */
    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, GroupRoleRepository groupRoleRepository, GroupUserRepository groupUserRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.groupRoleRepository = groupRoleRepository;
        this.groupUserRepository = groupUserRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Group> findByParentId(String parentId) {
        return groupRepository.findByParentId(parentId, Domains.sort(Sort.Direction.ASC, "name"));
    }

    @Override
    public List<GroupUsers> findGroupUsersByGroupId(String groupId) {
        List<GroupUser> lGroupUser = groupUserRepository.findByGroupId(groupId);
        List<GroupUsers> lsGroupUsers = Collections.synchronizedList(new ArrayList<>(lGroupUser.size()));
        lGroupUser.parallelStream().forEach(groupUser ->
                userRepository.findById(groupUser.getUserId()).map(user ->
                        lsGroupUsers.add(new GroupUsers(groupUser, user))
                ).orElseThrow(() -> new ServiceException("cannot find user by id: " + groupUser.getUserId()))
        );
        return lsGroupUsers;
    }

    @Override
    public List<GroupRole> findGroupRoleByGroupId(String groupId, String appId) {
        return groupRoleRepository.findByGroupIdAndAppIdAndType(groupId, appId, Role.Type.PUBLIC);
    }

    @Override
    @Transactional
    public Group create(Group group) {
        return groupRepository.save(group);
    }

    @Override
    @Transactional
    public Group update(String groupId, Group group) {
        return groupRepository.findById(groupId).map(oGroup -> {
            Objects.copyProperties(oGroup, group, Boolean.FALSE, "id", "creator", "createTime", "updater", "updateTime");
            return groupRepository.save(oGroup);
        }).orElseThrow(() -> new ServiceException("cannot find group by id: " + groupId));
    }

    @Override
    @Transactional
    public Group delete(String groupId) {
        if (!groupRepository.existsByParentId(groupId)) {
            return groupRepository.findById(groupId).map(group -> {
                groupUserRepository.deleteAll(groupUserRepository.findByGroupId(groupId));
                groupRepository.delete(group);
                return group;
            }).orElseThrow(() -> new ServiceException("cannot find group by id: " + groupId));
        }
        throw new ServiceException("group '" + groupId + "' has children.", "service.group.delete.failure.has-children");
    }

    @Override
    @Transactional
    public List<GroupUsers> createGroupUsers(String groupId, String[] userIds) {
        List<GroupUsers> lGroupUsers = Collections.synchronizedList(new ArrayList<>(userIds.length));
        Stream.of(userIds).parallel().forEach(userId -> {
            GroupUser groupUser = groupUserRepository.save(Models.newGroupUser(groupId, userId, null));
            userRepository.findById(userId).map(user -> lGroupUsers.add(new GroupUsers(groupUser, user))).orElseThrow(() -> new ServiceException("cannot find user by id: " + userId));
        });
        return lGroupUsers;
    }

    @Override
    @Transactional
    public GroupUsers deleteGroupUser(String groupUserId) {
        return groupUserRepository.findById(groupUserId).map(groupUser -> userRepository.findById(groupUser.getUserId()).map(user -> {
            groupUserRepository.delete(groupUser);
            return new GroupUsers(groupUser, user);
        }).orElseThrow(() -> new ServiceException("cannot find user by id: " + groupUser.getUserId()))).orElseThrow(() -> new ServiceException("cannot find group user by id: " + groupUserId));
    }

    @Override
    @Transactional
    public GroupUsers remarkGroupUser(String groupUserId, String remark) {
        return groupUserRepository.findById(groupUserId).map(groupUser -> userRepository.findById(groupUser.getUserId()).map(user -> {
            groupUser.setRemark(remark);
            groupUserRepository.save(groupUser);
            return new GroupUsers(groupUser, user);
        }).orElseThrow(() -> new ServiceException("cannot find user by id: " + groupUser.getUserId()))).orElseThrow(() -> new ServiceException("cannot find group user by id: " + groupUserId));
    }

    @Override
    @Transactional
    public List<GroupRole> bindRoles(String groupId, String appId, String[] roleIds) {
        List<GroupRole> lResult = Collections.synchronizedList(new ArrayList<>(roleIds.length));
        List<GroupRole> lGroupRole = groupRoleRepository.findByGroupIdAndAppIdAndType(groupId, appId, Role.Type.PUBLIC);

        Logger.info("delete {} GroupRole(s) by groupId: {}, appId: {}", lGroupRole.size(), groupId, appId);
        groupRoleRepository.deleteAll(lGroupRole);
        Stream.of(roleIds).parallel().forEach(roleId -> lResult.add(groupRoleRepository.save(Models.newGroupRole(groupId, roleId))));
        return lResult;
    }
}

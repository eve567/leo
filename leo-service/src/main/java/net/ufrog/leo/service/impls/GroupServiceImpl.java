package net.ufrog.leo.service.impls;

import net.ufrog.common.data.spring.Domains;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Objects;
import net.ufrog.leo.domain.Models;
import net.ufrog.leo.domain.models.Group;
import net.ufrog.leo.domain.models.GroupUser;
import net.ufrog.leo.domain.models.User;
import net.ufrog.leo.domain.repositories.GroupRepository;
import net.ufrog.leo.domain.repositories.GroupUserRepository;
import net.ufrog.leo.domain.repositories.UserRepository;
import net.ufrog.leo.service.GroupService;
import net.ufrog.leo.service.beans.GroupUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    /** 组织用户仓库 */
    private final GroupUserRepository groupUserRepository;

    /** 用户仓库 */
    private final UserRepository userRepository;

    /**
     * 构造函数
     *
     * @param groupRepository 组织仓库
     * @param groupUserRepository 组织用户仓库
     * @param userRepository 用户仓库
     */
    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, GroupUserRepository groupUserRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
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
        List<GroupUsers> lGroupUsers = new ArrayList<>(lGroupUser.size());
        lGroupUser.forEach(groupUser -> {
            lGroupUsers.add(new GroupUsers(groupUser, userRepository.findOne(groupUser.getUserId())));
        });
        return lGroupUsers;
    }

    @Override
    @Transactional
    public Group create(Group group) {
        return groupRepository.save(group);
    }

    @Override
    @Transactional
    public Group update(String groupId, Group group) {
        Group oGroup = groupRepository.findOne(groupId);
        Objects.copyProperties(oGroup, group, Boolean.FALSE, "id", "creator", "createTime", "updater", "updateTime");
        return groupRepository.save(oGroup);
    }

    @Override
    @Transactional
    public Group delete(String groupId) {
        if (!groupRepository.existsByParentId(groupId)) {
            Group group = groupRepository.findOne(groupId);
            List<GroupUser> lGroupUser = groupUserRepository.findByGroupId(groupId);

            groupUserRepository.delete(lGroupUser);
            groupRepository.delete(group);
            return group;
        }
        throw new ServiceException("group '" + groupId + "' has children.", "service.group.delete.failure.has-children");
    }

    @Override
    @Transactional
    public List<GroupUsers> createGroupUsers(String groupId, String[] userIds) {
        List<GroupUsers> lGroupUsers = new ArrayList<>(userIds.length);
        Stream.of(userIds).forEach(userId -> {
            GroupUser groupUser = groupUserRepository.save(Models.newGroupUser(groupId, userId, null));
            lGroupUsers.add(new GroupUsers(groupUser, userRepository.findOne(userId)));
        });
        return lGroupUsers;
    }

    @Override
    @Transactional
    public GroupUsers deleteGroupUser(String groupUserId) {
        GroupUser groupUser = groupUserRepository.findOne(groupUserId);
        User user = userRepository.findOne(groupUser.getUserId());
        groupUserRepository.delete(groupUser);
        return new GroupUsers(groupUser, user);
    }

    @Override
    @Transactional
    public GroupUsers remarkGroupUser(String groupUserId, String remark) {
        GroupUser groupUser = groupUserRepository.findOne(groupUserId);
        User user = userRepository.findOne(groupUser.getUserId());
        groupUser.setRemark(remark);
        groupUserRepository.save(groupUser);
        return new GroupUsers(groupUser, user);
    }
}

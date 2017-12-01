package net.ufrog.leo.service.impls;

import net.ufrog.common.data.spring.Domains;
import net.ufrog.leo.domain.models.Group;
import net.ufrog.leo.domain.models.GroupUser;
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
}

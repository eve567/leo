package net.ufrog.leo.service.impls;

import net.ufrog.common.data.spring.Domains;
import net.ufrog.leo.domain.models.Group;
import net.ufrog.leo.domain.repositories.GroupRepository;
import net.ufrog.leo.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 构造函数
     *
     * @param groupRepository 组织仓库
     */
    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> findByParentId(String parentId) {
        return groupRepository.findByParentId(parentId, Domains.sort(Sort.Direction.ASC, "name"));
    }

    @Override
    @Transactional
    public Group create(Group group) {
        return groupRepository.save(group);
    }
}

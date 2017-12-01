package net.ufrog.leo.console.controllers;

import net.ufrog.common.Result;
import net.ufrog.common.app.App;
import net.ufrog.common.utils.Objects;
import net.ufrog.leo.domain.models.Group;
import net.ufrog.leo.service.GroupService;
import net.ufrog.leo.service.beans.GroupUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 组织架构控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-11-20
 * @since 0.1
 */
@Controller
@RequestMapping("/group")
public class GroupController {

    /** 组织业务接口 */
    private final GroupService groupService;

    /**
     * 构造函数
     *
     * @param groupService 组织业务接口
     */
    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * 索引
     *
     * @return view for group/index.html
     */
    @GetMapping({"", "/", "/index"})
    public String index() {
        return "group/index";
    }

    /**
     * 查询
     *
     * @param parentId 上级编号
     * @return 组织列表
     */
    @GetMapping("/find/{parentId}")
    @ResponseBody
    public List<Group> find(@PathVariable("parentId") String parentId) {
        return groupService.findByParentId(parentId);
    }

    /**
     * 查询组织用户关系
     *
     * @param groupId 组织编号
     * @return 组织用户列表
     */
    @GetMapping("/users/{groupId}")
    @ResponseBody
    public List<GroupUsers> findGroupUsers(@PathVariable("groupId") String groupId) {
        List<GroupUsers> lGroupUsers = groupService.findGroupUsersByGroupId(groupId);
        lGroupUsers.forEach(groupUsers -> groupUsers.getUser().setPassword(null));
        return lGroupUsers;
    }

    /**
     * 创建组织
     *
     * @param group 组织对象
     * @return 创建结果
     */
    @PostMapping("/create")
    @ResponseBody
    public Result<Group> create(@RequestBody Group group) {
        Objects.trimStringFields(group);
        return Result.success(groupService.create(group), App.message("team.group.create.success", group.getName()));
    }
}

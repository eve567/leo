package net.ufrog.leo.console.controllers;

import net.ufrog.common.Result;
import net.ufrog.common.app.App;
import net.ufrog.common.utils.Objects;
import net.ufrog.common.utils.Strings;
import net.ufrog.leo.console.forms.GroupRoleBindForm;
import net.ufrog.leo.console.forms.GroupUserForm;
import net.ufrog.leo.console.forms.RemarkForm;
import net.ufrog.leo.domain.models.Group;
import net.ufrog.leo.domain.models.GroupRole;
import net.ufrog.leo.service.GroupService;
import net.ufrog.leo.service.beans.GroupUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
     * 查询组织角色关系
     *
     * @param groupId 组织编号
     * @param appId 应用编号
     * @return 组织角色关系列表
     */
    @GetMapping("/roles/{groupId}/{appId}")
    @ResponseBody
    public List<String> findGroupRoles(@PathVariable("groupId") String groupId, @PathVariable("appId") String appId) {
        return groupService.findGroupRoleByGroupId(groupId, appId).stream().map(GroupRole::getRoleId).collect(Collectors.toList());
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

    /**
     * 更新组织
     *
     * @param groupId 组织编号
     * @param group 组织对象
     * @return 更新结果
     */
    @PutMapping("/update/{groupId}")
    @ResponseBody
    public Result<Group> update(@PathVariable("groupId") String groupId, @RequestBody Group group) {
        Objects.trimStringFields(group);
        return Result.success(groupService.update(groupId, group), App.message("team.group.update.success", group.getName()));
    }

    /**
     * 删除组织
     *
     * @param groupId 组织编号
     * @return 删除结果
     */
    @DeleteMapping("/delete/{groupId}")
    @ResponseBody
    public Result<Group> delete(@PathVariable("groupId") String groupId) {
        Group group = groupService.delete(groupId);
        return Result.success(group, App.message("team.group.delete.success", group.getName()));
    }

    /**
     * 创建组织用户关系
     *
     * @param groupId 组织编号
     * @param groupUserForm 组织用户表单
     * @return 创建关系结果
     */
    @PostMapping("/member/add/{groupId}")
    @ResponseBody
    public Result<List<GroupUsers>> createGroupUser(@PathVariable("groupId") String groupId, @RequestBody GroupUserForm groupUserForm) {
        List<GroupUsers> lGroupUsers = groupService.createGroupUsers(groupId, groupUserForm.getIds());
        String userNames = lGroupUsers.stream().map(GroupUsers::getName).collect(Collectors.joining(", "));
        return Result.success(lGroupUsers, App.message("team.group.member.add.success", userNames));
    }

    /**
     * 删除组织用户关系
     *
     * @param groupUserId 组织用户编号
     * @return 删除关系结果
     */
    @DeleteMapping("/member/delete/{groupUserId}")
    @ResponseBody
    public Result<GroupUsers> deleteGroupUser(@PathVariable("groupUserId") String groupUserId) {
        GroupUsers groupUsers = groupService.deleteGroupUser(groupUserId);
        return Result.success(groupUsers, App.message("team.group.member.remove.success", groupUsers.getName()));
    }

    /**
     * 备注组织用户关系
     *
     * @param groupUserId 组织用户编号
     * @param remarkForm 备注表单
     * @return 备注结果
     */
    @PutMapping("/member/remark/{groupUserId}")
    @ResponseBody
    public Result<GroupUsers> remarkGroupUser(@PathVariable("groupUserId") String groupUserId, @RequestBody RemarkForm remarkForm) {
        GroupUsers groupUsers = groupService.remarkGroupUser(groupUserId, Strings.fromUnicode(remarkForm.getRemark()));
        return Result.success(groupUsers, App.message("team.group.member.remark.success", groupUsers.getName(), groupUsers.getGroupUser().getRemark()));
    }

    /**
     * 绑定角色
     *
     * @param groupRoleBindForm 组织角色绑定表单
     * @return 绑定结果
     */
    @PostMapping("/bind_roles")
    @ResponseBody
    public Result<List<GroupRole>> bindRoles(@RequestBody GroupRoleBindForm groupRoleBindForm) {
        List<GroupRole> lGroupRole = groupService.bindRoles(groupRoleBindForm.getGroupId(), groupRoleBindForm.getAppId(), groupRoleBindForm.getRoleIds());
        return Result.success(lGroupRole, App.message("team.group.bind-role.success"));
    }
}

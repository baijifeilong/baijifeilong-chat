package io.github.baijifeilong.chat.controller;

import io.github.baijifeilong.chat.domain.ApiPage;
import io.github.baijifeilong.chat.domain.ApiSuccess;
import io.github.baijifeilong.chat.domain.Group;
import io.github.baijifeilong.chat.domain.User;
import io.github.baijifeilong.chat.service.GroupService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/16 下午9:53
 * <p>
 * 群组控制器
 */
@RestController
public class GroupController {

    @Resource
    @NotNull
    private GroupService groupService;

    /**
     * 根据用户ID查询创建的群组列表
     *
     * @param userId 用户ID
     * @return 用户创建的群组列表
     */
    @GetMapping("/users/{userId}/ownedGroups")
    public ApiSuccess<List<Group>> ownedGroups(@PathVariable("userId") int userId) {
        List<Group> groups = groupService.findGroupsByOwnerId(userId);
        return ApiSuccess.of(groups);
    }

    /**
     * 根据用户ID查询加入的群组列表
     *
     * @param userId 用户ID
     * @return 用户加入的群组列表
     */
    @GetMapping("/users/{userId}/joinedGroups")
    public ApiSuccess<List<Group>> joinedGroups(@PathVariable("userId") int userId) {
        List<Group> groups = groupService.findJoinedGroupsByUserId(userId);
        return ApiSuccess.of(groups);
    }

    /**
     * 查询全部群组信息
     *
     * @param page .
     * @param size .
     * @return 群组分页
     */
    @GetMapping("/groups")
    public ApiSuccess<ApiPage<Group>> index(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<Group> groupPage = groupService.findAllGroups(page - 1, size);
        return ApiSuccess.ofPage(groupPage);
    }

    /**
     * 查询群组成员
     *
     * @param groupId 群组ID
     * @return 群组成员列表
     */
    @GetMapping("/groups/{groupId}/members")
    public ApiSuccess<List<User>> members(@PathVariable("groupId") int groupId) {
        List<User> users = groupService.findMembersByGroupId(groupId);
        return ApiSuccess.of(users);
    }

    /**
     * 查询群组信息
     *
     * @param groupId 群组ID
     * @return 群组信息
     */
    @GetMapping("/groups/{groupId}")
    public ApiSuccess<Group> showGroup(@PathVariable("groupId") int groupId) {
        Group group = groupService.findGroupByGroupIdOrThrow(groupId);
        return ApiSuccess.of(group);
    }
}

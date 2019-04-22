package io.github.baijifeilong.chat.controller;

import io.github.baijifeilong.chat.domain.ApiPage;
import io.github.baijifeilong.chat.domain.ApiSuccess;
import io.github.baijifeilong.chat.domain.User;
import io.github.baijifeilong.chat.service.GroupService;
import io.github.baijifeilong.chat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * Created by BaiJiFeiLong@gmail.com at 2018/12/20 下午8:56
 * <p>
 * 用户控制器
 */
@RestController
@Slf4j
public class UserController {

    @Resource
    @NotNull
    private UserService userService;

    @Resource
    @NotNull
    private GroupService groupService;

    /**
     * 登录(测试用)
     *
     * @param mobile   手机号
     * @param password 密码
     * @return 用户信息
     */
    @PostMapping("/users/login")
    public ApiSuccess<User> login(
            @RequestParam("mobile") String mobile,
            @RequestParam("password") String password
    ) {
        log.info("正在登录用户: {}=>{}", mobile, password);
        User user = userService.findUserByMobileOrThrow(mobile);
        userService.verifyPasswordOrThrow(password, user.getEncryptedPassword());
        log.info("用户登录成功: {}", user);
        return ApiSuccess.of(user);
    }

    /**
     * 查询加入了任意群组的全部用户
     *
     * @param page .
     * @param size .
     * @return 用户分页
     */
    @GetMapping("/users")
    public ApiSuccess<ApiPage<User>> queryUsersInAnyGroup(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<User> userPage = groupService.findUsersInAnyGroup(page - 1, size);
        return ApiSuccess.ofPage(userPage);
    }

    /**
     * 查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping("/users/{userId}")
    public ApiSuccess<User> queryUserByUserId(@PathVariable("userId") int userId) {
        User user = userService.findUserByUserIdOrThrow(userId);
        return ApiSuccess.of(user);
    }
}

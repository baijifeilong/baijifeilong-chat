package io.github.baijifeilong.chat.controller;

import io.github.baijifeilong.chat.domain.*;
import io.github.baijifeilong.chat.exception.BizException;
import io.github.baijifeilong.chat.exception.BizExceptionEnum;
import io.github.baijifeilong.chat.service.ContactService;
import io.github.baijifeilong.chat.service.GroupService;
import io.github.baijifeilong.chat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/25 下午4:21
 * <p>
 * MQTT鉴权控制器
 */
@RestController
@Slf4j
public class AuthController {

    @Resource
    private UserService userService;

    @Resource
    private GroupService groupService;

    @Resource
    private ContactService contactService;

    /**
     * 用户消息主题正则表达式
     */
    private Pattern userMessageTopicPattern = Pattern.compile("/users/(\\w+)/messages");

    /**
     * 群组消息主题正则表达式
     */
    private Pattern groupMessageTopicPattern = Pattern.compile("/groups/(\\d+)/messages");

    /**
     * 判断用户名密码是否匹配。不匹配则返回403
     *
     * @param username 用户名
     * @param password 密码
     * @return .
     */
    @PostMapping("/mosquitto/auth")
    public ApiSuccess<String> mosquittoAuth(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        log.info("正在认证用户身份: {} => {}", username, password);
        User user = userService.findUserByMobileOrThrow(username);
        log.info("用户: {}", user);
        userService.verifyPasswordOrThrow(password, user.getEncryptedPassword());
        log.info("密码正确: {}", user);
        return ApiSuccess.of("OK");
    }

    /**
     * 判断用户是否是超级用户。非超级用户返回403
     *
     * @param username 用户名
     * @return .
     */
    @PostMapping("/mosquitto/superuser")
    public ApiSuccess<String> mosquittoSuperuser(
            @RequestParam("username") String username
    ) {
        log.info("正在查询用户是否为超级用户");
        String[] superusers = new String[]{"18888888888"};
        log.info("当前超级用户列表: {}", Arrays.toString(superusers));
        if (!Arrays.asList(superusers).contains(username)) {
            log.info("非超级用户: {}", username);
            throw new NotSuperuserException();
        }
        log.info("是超级用户: {}", username);
        return ApiSuccess.of("OK");
    }

    /**
     * 判断用户对指定主题是否有权限执行指定操作
     *
     * @param username 用户名
     * @param topic    主题
     * @param acc      操作 包括发布、订阅
     * @return .
     */
    @PostMapping("/mosquitto/acl")
    public ApiSuccess<String> mosquittoAcl(
            @RequestParam("username") String username,
            @RequestParam("topic") String topic,
            @RequestParam("acc") String acc
    ) {
        log.info("正在校验用户权限: {} => {} => {}", username, acc, topic);
        acc = acc.replace("4", "1"); // TODO 插件传入参数与官方文档描述不一致，或有隐患
        Assertions.assertThat(acc).isIn("1", "2");
        PubOrSub pubOrSub = acc.equals("1") ? PubOrSub.SUB : PubOrSub.PUB;
        log.info("校验参数: {} => {} => {}", username, acc, topic);
        User user = userService.findUserByMobileOrThrow(username);
        log.info("当前用户: {}", user);

        Matcher matcher = userMessageTopicPattern.matcher(topic);
        if (matcher.matches()) {
            log.info("是单聊主题: {}", topic);
            int theUserId = Integer.parseInt(matcher.group(1));
            log.info("对方: {}", theUserId);
            if (pubOrSub.equals(PubOrSub.SUB)) {
                log.info("当前请求为订阅单聊消息");
                if (theUserId == user.getUserId()) {
                    log.info("订阅自己的消息，可以订阅");
                    return ApiSuccess.of("OK");
                } else {
                    log.info("订阅他人的消息，不能订阅");
                    throw new RuntimeException("用户无法订阅他人消息");
                }
            } else {
                log.info("当前请求为发布单聊消息");
                User theUser = userService.findUserByUserIdOrThrow(theUserId);
                log.info("对方: {}", user);
                if (isMyFriend(user.getUserId(), theUser.getUserId())) {
                    log.info("发布消息给好友，可以发布");
                    return ApiSuccess.of("OK");
                } else {
                    log.info("发布消息给非好友，不能发布");
                    throw new RuntimeException("永不不能发布消息给非好友");
                }
            }
        }

        matcher = groupMessageTopicPattern.matcher(topic);
        if (matcher.matches()) {
            log.info("是群聊主题: {}", topic);
            int groupId = Integer.parseInt(matcher.group(1));
            log.info("群组ID: {}", groupId);
            if (isInGroup(user.getUserId(), groupId)) {
                log.info("用户加入了此群组，可以订阅或发布消息");
                return ApiSuccess.of("OK");
            } else {
                log.info("用户未加入此群组，不能订阅或发布消息");
                throw new RuntimeException("用户未加入此群组，不能订阅或发布消息");
            }
        }

        throw new RuntimeException(String.format("用户无此权限: %s => %s => %s", username, pubOrSub, topic));
    }

    /**
     * 判断指定用户是否为指定群组成员
     *
     * @param userId  用户ID
     * @param groupId 群组ID
     * @return .
     */
    private boolean isInGroup(int userId, int groupId) {
        List<Group> joinedGroups = groupService.findJoinedGroupsByUserId(userId);
        return joinedGroups.stream().map(Group::getGroupId).anyMatch($ -> $.equals(groupId));
    }

    /**
     * 判断一个用户是否为另一个用户的好友(联系人)
     *
     * @param myUserId      第一人称用户
     * @param anotherUserId 第三人称用户
     * @return .
     */
    private boolean isMyFriend(int myUserId, int anotherUserId) {
        List<Contact> contacts = contactService.findContactsByUserId(myUserId);
        return contacts.stream().map(ContactTable::getContactUserId).anyMatch($ -> $.equals(anotherUserId));
    }

    /**
     * 处理鉴权异常。对未知异常打印堆栈信息
     *
     * @param e 异常
     * @return 403
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Object handleException(Throwable e) {
        if (e instanceof NotSuperuserException) {
            log.info("非超级用户");
            return ApiFailure.of(403, "非超级用户");
        }
        log.info("认证失败/鉴权失败: {} : {}", e.getMessage(), e.getClass().getName());
        if (!(e instanceof BizException)) {
            log.error("异常栈:", e);
        }
        return ApiFailure.of(403, e.getMessage());
    }

    /**
     * 发布/订阅 枚举
     */
    enum PubOrSub {
        SUB, PUB
    }

    /**
     * 非超级用户异常
     */
    private static class NotSuperuserException extends BizException {
        NotSuperuserException() {
            super(BizExceptionEnum.FUZZY);
        }
    }
}

package io.github.baijifeilong.chat.controller;

import io.github.baijifeilong.chat.domain.ApiPage;
import io.github.baijifeilong.chat.domain.ApiSuccess;
import io.github.baijifeilong.chat.domain.Message;
import io.github.baijifeilong.chat.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/17 下午3:15
 * <p>
 * 消息控制器
 */
@RestController
public class MessageController {

    @Resource
    @NotNull
    private MessageService messageService;

    /**
     * 查询用户的历史消息
     *
     * @param userId 用户ID
     * @param page   .
     * @param size   .
     * @return 消息分页
     */
    @GetMapping("/users/{userId}/messages")
    public ApiSuccess<ApiPage<Message>> queryMessagesOfUser(
            @PathVariable("userId") int userId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size

    ) {
        Page<Message> messagePage = messageService.findMessagesByUserId(userId, page - 1, size);
        return ApiSuccess.ofPage(messagePage);
    }

    /**
     * 查询群组的历史消息
     *
     * @param groupId 群组ID
     * @param page    .
     * @param size    .
     * @return 消息分页
     */
    @GetMapping("/groups/{groupId}/messages")
    public ApiSuccess<ApiPage<Message>> messages(
            @PathVariable("groupId") int groupId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<Message> messagePage = messageService.findMessagesByGroupId(groupId, page - 1, size);
        return ApiSuccess.ofPage(messagePage);
    }

    /**
     * 查询两个用户之间的单聊历史消息
     *
     * @param userId        第一人称用户ID
     * @param anotherUserId 第三人称用户ID
     * @param page          .
     * @param size          .
     * @return 消息分页
     */
    @GetMapping("/users/{userId}/chatWith/{anotherUserId}/messages")
    public ApiSuccess<ApiPage<Message>> chatMessagesBetweenTwoUsers(
            @PathVariable("userId") int userId,
            @PathVariable("anotherUserId") int anotherUserId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<Message> messagePage = messageService.findChatMessagesBetweenTwoUsers(userId, anotherUserId, page - 1, size);
        return ApiSuccess.ofPage(messagePage);
    }
}

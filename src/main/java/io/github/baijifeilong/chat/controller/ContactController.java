package io.github.baijifeilong.chat.controller;

import io.github.baijifeilong.chat.domain.ApiSuccess;
import io.github.baijifeilong.chat.domain.Contact;
import io.github.baijifeilong.chat.service.ContactService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/17 下午9:19
 * <p>
 * 联系人(好友)控制器
 */
@RestController
public class ContactController {

    @Resource
    private ContactService contactService;

    /**
     * 根据用户ID查询联系人列表
     *
     * @param userId 用户ID
     * @return 联系人列表
     */
    @GetMapping("/users/{userId}/contacts")
    public ApiSuccess<List<Contact>> queryContactsByUserId(@PathVariable("userId") int userId) {
        List<Contact> contacts = contactService.findContactsByUserId(userId);
        return ApiSuccess.of(contacts);
    }
}

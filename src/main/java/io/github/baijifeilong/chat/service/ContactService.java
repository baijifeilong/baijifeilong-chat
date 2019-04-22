package io.github.baijifeilong.chat.service;

import io.github.baijifeilong.chat.domain.Contact;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static io.github.baijifeilong.chat.generated.jooq.chat.Tables.CONTACT;
import static io.github.baijifeilong.chat.generated.jooq.chat.Tables.USER;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/17 下午9:20
 * <p>
 * 联系人(好友)服务
 */
@Service
public class ContactService {

    @Resource
    private DSLContext dslContext;

    /**
     * 根据用户ID查询联系人列表
     *
     * @param userId 用户ID
     * @return 联系人列表
     */
    public List<Contact> findContactsByUserId(int userId) {
        return dslContext.select(CONTACT.fields())
                .select(USER.NICKNAME, USER.MOBILE)
                .from(CONTACT)
                .join(USER)
                .on(CONTACT.CONTACT_USER_ID.equal(USER.USER_ID.cast(Integer.class)))
                .where(CONTACT.MY_USER_ID.equal(userId))
                .fetch()
                .into(Contact.class);
    }
}

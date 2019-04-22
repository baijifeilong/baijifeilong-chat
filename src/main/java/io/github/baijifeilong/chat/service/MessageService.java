package io.github.baijifeilong.chat.service;

import io.github.baijifeilong.chat.domain.Message;
import io.github.baijifeilong.chat.domain.MessageTable;
import io.github.baijifeilong.chat.generated.jooq.chat.tables.records.MessageRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectSeekStep1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static io.github.baijifeilong.chat.generated.jooq.chat.Tables.MESSAGE;
import static io.github.baijifeilong.chat.generated.jooq.chat.Tables.USER;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/17 下午3:09
 * <p>
 * 消息服务
 */
@Service
public class MessageService {

    @Resource
    private DSLContext dslContext;

    /**
     * 创建消息并入库
     *
     * @param type    消息类型
     * @param fromId  发送方ID
     * @param toId    接收方ID
     * @param content 消息正文
     * @return 创建后的消息
     */
    public Message createMessage(MessageTable.Type type, int fromId, int toId, String content) {
        return dslContext.insertInto(MESSAGE)
                .set(MESSAGE.TYPE, type.name())
                .set(MESSAGE.FROM_ID, fromId)
                .set(MESSAGE.TO_ID, toId)
                .set(MESSAGE.CONTENT, content)
                .returning()
                .fetch()
                .into(Message.class)
                .get(0);
    }

    /**
     * 查询群组历史消息
     *
     * @param groupId 群组ID
     * @param page    .
     * @param size    .
     * @return 消息分页
     */
    public Page<Message> findMessagesByGroupId(int groupId, int page, int size) {
        SelectSeekStep1<Record, LocalDateTime> select = dslContext
                .select(MESSAGE.fields())
                .select(USER.NICKNAME)
                .from(MESSAGE)
                .join(USER)
                .on(MESSAGE.FROM_ID.equal(USER.USER_ID.cast(Integer.class)))
                .where(MESSAGE.TYPE.equal(Message.Type.GROUPCHAT.name()))
                .and(MESSAGE.TO_ID.equal(groupId))
                .orderBy(MESSAGE.CREATED_AT.desc());
        int total = dslContext.fetchCount(select);
        List<Message> messages = select.offset(page * size).limit(size).fetch().into(Message.class);
        return new PageImpl<>(messages, PageRequest.of(page, size), total);
    }

    /**
     * 查询用户历史消息(单聊+群聊,发送+接收)
     *
     * @param userId 用户ID
     * @param page   .
     * @param size   .
     * @return 消息分页
     */
    public Page<Message> findMessagesByUserId(int userId, int page, int size) {
        SelectSeekStep1<MessageRecord, LocalDateTime> select = dslContext
                .selectFrom(MESSAGE)
                .where(MESSAGE.TYPE.equal(Message.Type.CHAT.name())
                        .and(MESSAGE.FROM_ID.equal(userId).or(MESSAGE.TO_ID.equal(userId))))
                .or(MESSAGE.TYPE.equal(Message.Type.GROUPCHAT.name()).and(MESSAGE.FROM_ID.equal(userId)))
                .orderBy(MESSAGE.CREATED_AT.desc());
        int total = dslContext.fetchCount(select);
        List<Message> messages = select.offset(page * size).limit(size).fetchInto(Message.class);
        return new PageImpl<>(messages, PageRequest.of(page, size), total);
    }

    /**
     * 查询两个用户之间的单聊历史消息
     *
     * @param userId        用户ID
     * @param anotherUserId 对方用户ID
     * @param page          .
     * @param size          .
     * @return 消息分页
     */
    public Page<Message> findChatMessagesBetweenTwoUsers(int userId, int anotherUserId, int page, int size) {
        SelectSeekStep1<MessageRecord, LocalDateTime> select = dslContext
                .selectFrom(MESSAGE)
                .where(MESSAGE.TYPE.equal(Message.Type.CHAT.name()))
                .and(MESSAGE.FROM_ID.equal(userId).and(MESSAGE.TO_ID.equal(anotherUserId))
                        .or(MESSAGE.FROM_ID.eq(anotherUserId).and(MESSAGE.TO_ID.eq(userId))))
                .orderBy(MESSAGE.CREATED_AT.desc());
        int total = dslContext.fetchCount(select);
        List<Message> messages = select.offset(page * size).limit(size).fetchInto(Message.class);
        return new PageImpl<>(messages, PageRequest.of(page, size), total);
    }
}

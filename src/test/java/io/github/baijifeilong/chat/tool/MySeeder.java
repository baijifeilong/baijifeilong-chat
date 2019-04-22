package io.github.baijifeilong.chat.tool;

import com.zaxxer.hikari.HikariDataSource;
import io.github.baijifeilong.chat.domain.GroupUserRelation;
import io.github.baijifeilong.chat.domain.Message;
import io.github.baijifeilong.chat.generated.jooq.chat.tables.records.ContactRecord;
import io.github.baijifeilong.chat.generated.jooq.chat.tables.records.GroupRecord;
import io.github.baijifeilong.chat.generated.jooq.chat.tables.records.MessageRecord;
import io.github.baijifeilong.chat.generated.jooq.chat.tables.records.UserRecord;
import io.github.baijifeilong.chat.util.Articles;
import io.vavr.collection.Stream;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDSLContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static io.github.baijifeilong.chat.generated.jooq.chat.Tables.*;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/16 下午5:57
 * <p>
 * 数据库播种机
 */
public class MySeeder implements MyEnvironment {
    public static void main(String[] args) {
        DSLContext dslContext = new DefaultDSLContext(new HikariDataSource() {{
            setJdbcUrl(JDBC_URL);
            setUsername(JDBC_USERNAME);
            setPassword(JDBC_PASSWORD);
        }}, SQLDialect.MYSQL_8_0);

        String[] words = Articles.THOUSAND_WORDS;


        /// -1. 清库

        dslContext.truncate(GROUP).execute();
        dslContext.truncate(GROUP_USER_RELATION).execute();
        dslContext.truncate(MESSAGE).execute();
        dslContext.truncate(CONTACT).execute();

        /// 0. 播种用户表

        dslContext.insertInto(USER);

        dslContext.insertInto(USER)
                .set(USER.MOBILE, "18888888888")
                .set(USER.NICKNAME, Stream.ofAll(Arrays.stream(words)).shuffle().head().substring(1))
                .set(USER.ENCRYPTED_PASSWORD, DigestUtils.md5Hex("18888888888"))
                .execute();
        Stream.rangeClosed(1, 100).forEach($ -> {
            String mobile = 1 + RandomStringUtils.randomNumeric(10);
            dslContext.insertInto(USER)
                    .set(USER.MOBILE, mobile)
                    .set(USER.NICKNAME, Stream.ofAll(Arrays.stream(words)).shuffle().head().substring(1))
                    .set(USER.ENCRYPTED_PASSWORD, DigestUtils.md5Hex(mobile))
                    .execute();
        });

        List<UserRecord> users = dslContext.selectFrom(USER)
                .limit(100).fetch();
        Collections.shuffle(users);
        System.out.println(users);

        UserRecord me = dslContext.selectFrom(USER)
                .where(USER.MOBILE.equal("18888888888"))
                .fetchOne();
        System.out.println(me);

        /// 1. 播种群组表

        GroupRecord oneCenter = dslContext.insertInto(GROUP)
                .set(GROUP.OWNER_ID, me.getUserId())
                .set(GROUP.NAME, "一个中心")
                .returning().fetchOne();
        GroupRecord twoPoints = dslContext.insertInto(GROUP)
                .set(GROUP.OWNER_ID, me.getUserId())
                .set(GROUP.NAME, "两个基本点")
                .returning().fetchOne();
        GroupRecord threeWatches = dslContext.insertInto(GROUP)
                .set(GROUP.OWNER_ID, me.getUserId())
                .set(GROUP.NAME, "三个代表")
                .returning().fetchOne();
        GroupRecord fourModerns = dslContext.insertInto(GROUP)
                .set(GROUP.OWNER_ID, me.getUserId())
                .set(GROUP.NAME, "四个现代化")
                .returning().fetchOne();
        System.out.println(dslContext.selectFrom(GROUP).fetch());

        /// 2. 播种群组关系表

        dslContext.insertInto(GROUP_USER_RELATION)
                .set(GROUP_USER_RELATION.GROUP_ID, oneCenter.getGroupId())
                .set(GROUP_USER_RELATION.USER_ID, oneCenter.getOwnerId()).execute();
        dslContext.insertInto(GROUP_USER_RELATION)
                .set(GROUP_USER_RELATION.GROUP_ID, twoPoints.getGroupId())
                .set(GROUP_USER_RELATION.USER_ID, twoPoints.getOwnerId()).execute();
        Stream.ofAll(users).shuffle().take(1).forEach($ -> dslContext.insertInto(GROUP_USER_RELATION)
                .set(GROUP_USER_RELATION.GROUP_ID, twoPoints.getGroupId())
                .set(GROUP_USER_RELATION.USER_ID, $.getUserId()).execute());
        dslContext.insertInto(GROUP_USER_RELATION)
                .set(GROUP_USER_RELATION.GROUP_ID, threeWatches.getGroupId())
                .set(GROUP_USER_RELATION.USER_ID, threeWatches.getOwnerId()).execute();
        Stream.ofAll(users).shuffle().take(2).forEach($ -> dslContext.insertInto(GROUP_USER_RELATION)
                .set(GROUP_USER_RELATION.GROUP_ID, threeWatches.getGroupId())
                .set(GROUP_USER_RELATION.USER_ID, $.getUserId()).execute());
        dslContext.insertInto(GROUP_USER_RELATION)
                .set(GROUP_USER_RELATION.GROUP_ID, fourModerns.getGroupId())
                .set(GROUP_USER_RELATION.USER_ID, fourModerns.getOwnerId()).execute();
        Stream.ofAll(users).shuffle().take(3).forEach($ -> dslContext.insertInto(GROUP_USER_RELATION)
                .set(GROUP_USER_RELATION.GROUP_ID, fourModerns.getGroupId())
                .set(GROUP_USER_RELATION.USER_ID, $.getUserId()).execute());
        System.out.println(dslContext.selectFrom(GROUP_USER_RELATION).fetch());
        List<GroupUserRelation> relations = dslContext.selectFrom(GROUP_USER_RELATION)
                .fetch()
                .into(GroupUserRelation.class);
        System.out.println("Relations:");
        System.out.println(relations);


        /// 3. 播种联系人表

        List<Integer> userIds = relations.stream().map(GroupUserRelation::getUserId).distinct().collect(Collectors.toList());
        userIds.forEach(userId -> Stream.ofAll(userIds).remove(userId).forEach(contactId -> {
            boolean useRemarkName = ThreadLocalRandom.current().nextBoolean();
            String remarkName = Stream.ofAll(Arrays.stream(words)).shuffle().head().substring(0, 2);
            dslContext
                    .insertInto(CONTACT)
                    .set(CONTACT.MY_USER_ID, userId)
                    .set(CONTACT.CONTACT_USER_ID, contactId)
                    .set(CONTACT.REMARK_NAME, useRemarkName ? remarkName : null)
                    .execute();
        }));
        Result<ContactRecord> contactRelations = dslContext.selectFrom(CONTACT).fetch();
        System.out.println("Contacts:");
        System.out.println(contactRelations);

        /// 4. 播种消息表

        Stream.ofAll(relations).shuffle().forEach(relation -> Stream.continually(Object.class).take(ThreadLocalRandom.current().nextInt(1, 6)).forEach($ -> {
                    dslContext
                            .insertInto(MESSAGE)
                            .set(MESSAGE.TYPE, Message.Type.GROUPCHAT.name())
                            .set(MESSAGE.FROM_ID, relation.getUserId())
                            .set(MESSAGE.TO_ID, relation.getGroupId())
                            .set(MESSAGE.CONTENT, Stream.ofAll(Arrays.stream(words)).shuffle().head())
                            .set(MESSAGE.CREATED_AT, LocalDateTime.now().withNano(0).minusSeconds(ThreadLocalRandom.current().nextInt(24 * 3600 * 7)))
                            .execute();
                    dslContext
                            .insertInto(MESSAGE)
                            .set(MESSAGE.TYPE, Message.Type.CHAT.name())
                            .set(MESSAGE.FROM_ID, relation.getUserId())
                            .set(MESSAGE.TO_ID, Stream.ofAll(relations).map(GroupUserRelation::getUserId).remove(relation.getUserId()).shuffle().head())
                            .set(MESSAGE.CONTENT, Stream.ofAll(Arrays.stream(words)).shuffle().head())
                            .set(MESSAGE.CREATED_AT, LocalDateTime.now().withNano(0).minusSeconds(ThreadLocalRandom.current().nextInt(24 * 3600 * 7)))
                            .execute();
                }
        ));
        Result<MessageRecord> messageRecords = dslContext.selectFrom(MESSAGE).fetch();
        System.out.println(messageRecords);
    }
}

package io.github.baijifeilong.chat.service;

import io.github.baijifeilong.chat.domain.Group;
import io.github.baijifeilong.chat.domain.User;
import io.github.baijifeilong.chat.exception.FuzzyException;
import io.github.baijifeilong.chat.generated.jooq.chat.tables.records.GroupRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectLimitStep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static io.github.baijifeilong.chat.generated.jooq.chat.Tables.*;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/16 下午9:55
 * <p>
 * 群组服务
 */
@Service
public class GroupService {
    @Resource
    private DSLContext dslContext;

    /**
     * 根据群组ID查询群组信息
     *
     * @param groupId 群组ID
     * @return 群组信息
     */
    private Optional<Group> findGroupByGroupId(int groupId) {
        return dslContext
                .selectFrom(GROUP)
                .where(GROUP.GROUP_ID.equal(groupId))
                .fetchOptionalInto(Group.class);
    }

    /**
     * 根据群组ID查询群组信息，找不到则抛出异常
     *
     * @param groupId 群组ID
     * @return 群组信息
     */
    public Group findGroupByGroupIdOrThrow(int groupId) {
        return findGroupByGroupId(groupId).orElseThrow(() -> new FuzzyException("群组(id={})不存在", groupId));
    }

    /**
     * 查询用户创建的群组
     *
     * @param ownerId 群主ID
     * @return 群组列表
     */
    public List<Group> findGroupsByOwnerId(int ownerId) {
        return dslContext
                .selectFrom(GROUP)
                .where(GROUP.OWNER_ID.equal(ownerId))
                .limit(100)
                .fetchInto(Group.class);
    }

    /**
     * 查询用户加入的群组
     *
     * @param userId 用户ID
     * @return 群组列表
     */
    public List<Group> findJoinedGroupsByUserId(int userId) {
        return dslContext
                .selectDistinct(GROUP.fields())
                .from(GROUP_USER_RELATION)
                .join(GROUP)
                .on(GROUP_USER_RELATION.GROUP_ID.equal(GROUP.GROUP_ID))
                .where(GROUP_USER_RELATION.USER_ID.equal(userId))
                .fetch()
                .into(Group.class);
    }

    /**
     * 查询全部群组
     *
     * @param page .
     * @param size .
     * @return 群组分页
     */
    public Page<Group> findAllGroups(int page, int size) {
        SelectLimitStep<GroupRecord> select = dslContext
                .selectFrom(GROUP)
                .where(GROUP.DELETED_AT.isNull());
        int total = dslContext.fetchCount(select);
        List<Group> groups = select.offset(page * size).limit(size).fetch().into(Group.class);
        return new PageImpl<>(groups, PageRequest.of(page, size), total);
    }

    /**
     * 查询群组成员
     *
     * @param groupId 群组ID
     * @return 群组成员列表
     */
    public List<User> findMembersByGroupId(int groupId) {
        return dslContext
                .select(USER.fields())
                .from(GROUP)
                .join(GROUP_USER_RELATION)
                .on(GROUP.GROUP_ID.equal(GROUP_USER_RELATION.GROUP_ID))
                .join(USER)
                .on(GROUP_USER_RELATION.USER_ID.equal(USER.USER_ID.cast(Integer.class)))
                .where(GROUP.GROUP_ID.equal(groupId))
                .fetch()
                .into(User.class);
    }

    /**
     * 查询加入了任意群组的所有用户
     *
     * @param page .
     * @param size .
     * @return 用户分页
     */
    public Page<User> findUsersInAnyGroup(int page, int size) {
        SelectLimitStep<Record> select = dslContext
                .selectDistinct(USER.fields())
                .from(GROUP_USER_RELATION)
                .join(USER)
                .on(GROUP_USER_RELATION.USER_ID.equal(USER.USER_ID.cast(Integer.class)));
        int total = dslContext.fetchCount(select);
        List<User> users = select.offset(page * size).limit(size).fetch().into(User.class);
        return new PageImpl<>(users, PageRequest.of(page, size), total);
    }
}

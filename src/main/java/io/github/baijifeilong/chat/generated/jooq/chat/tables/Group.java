/*
 * This file is generated by jOOQ.
 */
package io.github.baijifeilong.chat.generated.jooq.chat.tables;


import io.github.baijifeilong.chat.generated.jooq.chat.Chat;
import io.github.baijifeilong.chat.generated.jooq.chat.Indexes;
import io.github.baijifeilong.chat.generated.jooq.chat.Keys;
import io.github.baijifeilong.chat.generated.jooq.chat.tables.records.GroupRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * 群组
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Group extends TableImpl<GroupRecord> {

    private static final long serialVersionUID = -1524217094;

    /**
     * The reference instance of <code>chat.group</code>
     */
    public static final Group GROUP = new Group();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GroupRecord> getRecordType() {
        return GroupRecord.class;
    }

    /**
     * The column <code>chat.group.group_id</code>. 群组ID
     */
    public final TableField<GroupRecord, Integer> GROUP_ID = createField("group_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "群组ID");

    /**
     * The column <code>chat.group.owner_id</code>. 群主ID
     */
    public final TableField<GroupRecord, Integer> OWNER_ID = createField("owner_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "群主ID");

    /**
     * The column <code>chat.group.name</code>. 群组名称
     */
    public final TableField<GroupRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "群组名称");

    /**
     * The column <code>chat.group.created_at</code>. 创建时间
     */
    public final TableField<GroupRecord, LocalDateTime> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "创建时间");

    /**
     * The column <code>chat.group.updated_at</code>. 更新时间
     */
    public final TableField<GroupRecord, LocalDateTime> UPDATED_AT = createField("updated_at", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "更新时间");

    /**
     * The column <code>chat.group.deleted_at</code>. 删除时间
     */
    public final TableField<GroupRecord, LocalDateTime> DELETED_AT = createField("deleted_at", org.jooq.impl.SQLDataType.LOCALDATETIME, this, "删除时间");

    /**
     * Create a <code>chat.group</code> table reference
     */
    public Group() {
        this(DSL.name("group"), null);
    }

    /**
     * Create an aliased <code>chat.group</code> table reference
     */
    public Group(String alias) {
        this(DSL.name(alias), GROUP);
    }

    /**
     * Create an aliased <code>chat.group</code> table reference
     */
    public Group(Name alias) {
        this(alias, GROUP);
    }

    private Group(Name alias, Table<GroupRecord> aliased) {
        this(alias, aliased, null);
    }

    private Group(Name alias, Table<GroupRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("群组"));
    }

    public <O extends Record> Group(Table<O> child, ForeignKey<O, GroupRecord> key) {
        super(child, key, GROUP);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Chat.CHAT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.GROUP_CREATED_AT, Indexes.GROUP_DELETED_AT, Indexes.GROUP_NAME, Indexes.GROUP_OWNER_ID, Indexes.GROUP_PRIMARY, Indexes.GROUP_UPDATED_AT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<GroupRecord, Integer> getIdentity() {
        return Keys.IDENTITY_GROUP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<GroupRecord> getPrimaryKey() {
        return Keys.KEY_GROUP_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<GroupRecord>> getKeys() {
        return Arrays.<UniqueKey<GroupRecord>>asList(Keys.KEY_GROUP_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Group as(String alias) {
        return new Group(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Group as(Name alias) {
        return new Group(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Group rename(String name) {
        return new Group(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Group rename(Name name) {
        return new Group(name, null);
    }
}

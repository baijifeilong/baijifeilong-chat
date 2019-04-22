/*
 * This file is generated by jOOQ.
 */
package io.github.baijifeilong.chat.generated.jooq.chat.tables;


import io.github.baijifeilong.chat.generated.jooq.chat.Chat;
import io.github.baijifeilong.chat.generated.jooq.chat.Indexes;
import io.github.baijifeilong.chat.generated.jooq.chat.Keys;
import io.github.baijifeilong.chat.generated.jooq.chat.tables.records.MessageRecord;

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
 * 消息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Message extends TableImpl<MessageRecord> {

    private static final long serialVersionUID = -637062066;

    /**
     * The reference instance of <code>chat.message</code>
     */
    public static final Message MESSAGE = new Message();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MessageRecord> getRecordType() {
        return MessageRecord.class;
    }

    /**
     * The column <code>chat.message.message_id</code>. 消息ID
     */
    public final TableField<MessageRecord, Long> MESSAGE_ID = createField("message_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "消息ID");

    /**
     * The column <code>chat.message.type</code>. 消息类型
     */
    public final TableField<MessageRecord, String> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR(16).nullable(false), this, "消息类型");

    /**
     * The column <code>chat.message.from_id</code>. 发送方ID
     */
    public final TableField<MessageRecord, Integer> FROM_ID = createField("from_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "发送方ID");

    /**
     * The column <code>chat.message.to_id</code>. 接收方ID
     */
    public final TableField<MessageRecord, Integer> TO_ID = createField("to_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "接收方ID");

    /**
     * The column <code>chat.message.content</code>. 消息正文
     */
    public final TableField<MessageRecord, String> CONTENT = createField("content", org.jooq.impl.SQLDataType.VARCHAR(1024).nullable(false), this, "消息正文");

    /**
     * The column <code>chat.message.created_at</code>. 创建时间
     */
    public final TableField<MessageRecord, LocalDateTime> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "创建时间");

    /**
     * Create a <code>chat.message</code> table reference
     */
    public Message() {
        this(DSL.name("message"), null);
    }

    /**
     * Create an aliased <code>chat.message</code> table reference
     */
    public Message(String alias) {
        this(DSL.name(alias), MESSAGE);
    }

    /**
     * Create an aliased <code>chat.message</code> table reference
     */
    public Message(Name alias) {
        this(alias, MESSAGE);
    }

    private Message(Name alias, Table<MessageRecord> aliased) {
        this(alias, aliased, null);
    }

    private Message(Name alias, Table<MessageRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("消息"));
    }

    public <O extends Record> Message(Table<O> child, ForeignKey<O, MessageRecord> key) {
        super(child, key, MESSAGE);
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
        return Arrays.<Index>asList(Indexes.MESSAGE_CONTENT, Indexes.MESSAGE_FROM_ID, Indexes.MESSAGE_PRIMARY, Indexes.MESSAGE_TO_ID, Indexes.MESSAGE_TYPE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<MessageRecord, Long> getIdentity() {
        return Keys.IDENTITY_MESSAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MessageRecord> getPrimaryKey() {
        return Keys.KEY_MESSAGE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MessageRecord>> getKeys() {
        return Arrays.<UniqueKey<MessageRecord>>asList(Keys.KEY_MESSAGE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message as(String alias) {
        return new Message(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message as(Name alias) {
        return new Message(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Message rename(String name) {
        return new Message(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Message rename(Name name) {
        return new Message(name, null);
    }
}

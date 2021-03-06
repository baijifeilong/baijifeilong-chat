/*
 * This file is generated by jOOQ.
 */
package io.github.baijifeilong.chat.generated.jooq.chat;


import io.github.baijifeilong.chat.generated.jooq.chat.tables.Contact;
import io.github.baijifeilong.chat.generated.jooq.chat.tables.Group;
import io.github.baijifeilong.chat.generated.jooq.chat.tables.GroupUserRelation;
import io.github.baijifeilong.chat.generated.jooq.chat.tables.Message;
import io.github.baijifeilong.chat.generated.jooq.chat.tables.User;
import io.github.baijifeilong.chat.generated.jooq.chat.tables.records.ContactRecord;
import io.github.baijifeilong.chat.generated.jooq.chat.tables.records.GroupRecord;
import io.github.baijifeilong.chat.generated.jooq.chat.tables.records.GroupUserRelationRecord;
import io.github.baijifeilong.chat.generated.jooq.chat.tables.records.MessageRecord;
import io.github.baijifeilong.chat.generated.jooq.chat.tables.records.UserRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>chat</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<ContactRecord, Long> IDENTITY_CONTACT = Identities0.IDENTITY_CONTACT;
    public static final Identity<GroupRecord, Integer> IDENTITY_GROUP = Identities0.IDENTITY_GROUP;
    public static final Identity<GroupUserRelationRecord, Long> IDENTITY_GROUP_USER_RELATION = Identities0.IDENTITY_GROUP_USER_RELATION;
    public static final Identity<MessageRecord, Long> IDENTITY_MESSAGE = Identities0.IDENTITY_MESSAGE;
    public static final Identity<UserRecord, Integer> IDENTITY_USER = Identities0.IDENTITY_USER;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ContactRecord> KEY_CONTACT_PRIMARY = UniqueKeys0.KEY_CONTACT_PRIMARY;
    public static final UniqueKey<ContactRecord> KEY_CONTACT_UNIQUE_MY_USER_ID_CONTACT_USER_ID = UniqueKeys0.KEY_CONTACT_UNIQUE_MY_USER_ID_CONTACT_USER_ID;
    public static final UniqueKey<GroupRecord> KEY_GROUP_PRIMARY = UniqueKeys0.KEY_GROUP_PRIMARY;
    public static final UniqueKey<GroupUserRelationRecord> KEY_GROUP_USER_RELATION_PRIMARY = UniqueKeys0.KEY_GROUP_USER_RELATION_PRIMARY;
    public static final UniqueKey<GroupUserRelationRecord> KEY_GROUP_USER_RELATION_UNIQUE_GROUP_ID_USER_ID = UniqueKeys0.KEY_GROUP_USER_RELATION_UNIQUE_GROUP_ID_USER_ID;
    public static final UniqueKey<MessageRecord> KEY_MESSAGE_PRIMARY = UniqueKeys0.KEY_MESSAGE_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = UniqueKeys0.KEY_USER_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_MOBILE = UniqueKeys0.KEY_USER_MOBILE;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<ContactRecord, Long> IDENTITY_CONTACT = Internal.createIdentity(Contact.CONTACT, Contact.CONTACT.RELATION_ID);
        public static Identity<GroupRecord, Integer> IDENTITY_GROUP = Internal.createIdentity(Group.GROUP, Group.GROUP.GROUP_ID);
        public static Identity<GroupUserRelationRecord, Long> IDENTITY_GROUP_USER_RELATION = Internal.createIdentity(GroupUserRelation.GROUP_USER_RELATION, GroupUserRelation.GROUP_USER_RELATION.RELATION_ID);
        public static Identity<MessageRecord, Long> IDENTITY_MESSAGE = Internal.createIdentity(Message.MESSAGE, Message.MESSAGE.MESSAGE_ID);
        public static Identity<UserRecord, Integer> IDENTITY_USER = Internal.createIdentity(User.USER, User.USER.USER_ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<ContactRecord> KEY_CONTACT_PRIMARY = Internal.createUniqueKey(Contact.CONTACT, "KEY_contact_PRIMARY", Contact.CONTACT.RELATION_ID);
        public static final UniqueKey<ContactRecord> KEY_CONTACT_UNIQUE_MY_USER_ID_CONTACT_USER_ID = Internal.createUniqueKey(Contact.CONTACT, "KEY_contact_unique_my_user_id_contact_user_id", Contact.CONTACT.MY_USER_ID, Contact.CONTACT.CONTACT_USER_ID);
        public static final UniqueKey<GroupRecord> KEY_GROUP_PRIMARY = Internal.createUniqueKey(Group.GROUP, "KEY_group_PRIMARY", Group.GROUP.GROUP_ID);
        public static final UniqueKey<GroupUserRelationRecord> KEY_GROUP_USER_RELATION_PRIMARY = Internal.createUniqueKey(GroupUserRelation.GROUP_USER_RELATION, "KEY_group_user_relation_PRIMARY", GroupUserRelation.GROUP_USER_RELATION.RELATION_ID);
        public static final UniqueKey<GroupUserRelationRecord> KEY_GROUP_USER_RELATION_UNIQUE_GROUP_ID_USER_ID = Internal.createUniqueKey(GroupUserRelation.GROUP_USER_RELATION, "KEY_group_user_relation_unique_group_id_user_id", GroupUserRelation.GROUP_USER_RELATION.GROUP_ID, GroupUserRelation.GROUP_USER_RELATION.USER_ID);
        public static final UniqueKey<MessageRecord> KEY_MESSAGE_PRIMARY = Internal.createUniqueKey(Message.MESSAGE, "KEY_message_PRIMARY", Message.MESSAGE.MESSAGE_ID);
        public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = Internal.createUniqueKey(User.USER, "KEY_user_PRIMARY", User.USER.USER_ID);
        public static final UniqueKey<UserRecord> KEY_USER_MOBILE = Internal.createUniqueKey(User.USER, "KEY_user_mobile", User.USER.MOBILE);
    }
}

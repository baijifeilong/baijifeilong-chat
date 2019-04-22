/*
 * This file is generated by jOOQ.
 */
package io.github.baijifeilong.chat.generated.jooq.thatcher;


import io.github.baijifeilong.chat.generated.jooq.thatcher.tables.GreatUser;
import io.github.baijifeilong.chat.generated.jooq.thatcher.tables.records.GreatUserRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>thatcher</code> schema.
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

    public static final Identity<GreatUserRecord, Integer> IDENTITY_GREAT_USER = Identities0.IDENTITY_GREAT_USER;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<GreatUserRecord> KEY_GREAT_USER_PRIMARY = UniqueKeys0.KEY_GREAT_USER_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<GreatUserRecord, Integer> IDENTITY_GREAT_USER = Internal.createIdentity(GreatUser.GREAT_USER, GreatUser.GREAT_USER.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<GreatUserRecord> KEY_GREAT_USER_PRIMARY = Internal.createUniqueKey(GreatUser.GREAT_USER, "KEY_great_user_PRIMARY", GreatUser.GREAT_USER.ID);
    }
}

-- 数据库DDL

USE chat;

DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    user_id            INT PRIMARY KEY AUTO_INCREMENT,
    mobile             CHAR(11)    NOT NULL UNIQUE,
    encrypted_password VARCHAR(32) NOT NULL,
    nickname           VARCHAR(32) NOT NULL
);

DROP TABLE IF EXISTS `group`;
CREATE TABLE `group`
(
    group_id   INT PRIMARY KEY AUTO_INCREMENT
        COMMENT '群组ID',
    owner_id   INT         NOT NULL
        COMMENT '群主ID',
    name       VARCHAR(64) NOT NULL
        COMMENT '群组名称',
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
        COMMENT '创建时间',
    updated_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
        COMMENT '更新时间',
    deleted_at TIMESTAMP   NULL     DEFAULT NULL
        COMMENT '删除时间',
    INDEX owner_id (owner_id),
    INDEX name (name),
    INDEX created_at (created_at),
    INDEX updated_at (updated_at),
    INDEX deleted_at (deleted_at)
)
    COMMENT '群组'
    CHARSET 'utf8';

DROP TABLE IF EXISTS group_user_relation;
CREATE TABLE group_user_relation
(
    relation_id BIGINT PRIMARY KEY AUTO_INCREMENT
        COMMENT '主键ID',
    group_id    INT       NOT NULL
        COMMENT '群组ID',
    user_id     INT       NOT NULL
        COMMENT '用户ID',
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        COMMENT '创建时间',
    UNIQUE unique_group_id_user_id (group_id, user_id)
        COMMENT '唯一索引(群组ID=>用户ID)',
    INDEX user_id (user_id)
)
    COMMENT '群组成员'
    CHARSET 'utf8';

DROP TABLE IF EXISTS message;
CREATE TABLE message
(
    message_id BIGINT PRIMARY KEY AUTO_INCREMENT
        COMMENT '消息ID',
    type       VARCHAR(16)   NOT NULL
        COMMENT '消息类型',
    from_id    INT           NOT NULL
        COMMENT '发送方ID',
    to_id      INT           NOT NULL
        COMMENT '接收方ID',
    content    VARCHAR(1024) NOT NULL
        COMMENT '消息正文',
    created_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
        COMMENT '创建时间',
    INDEX type (type),
    INDEX from_id (from_id),
    INDEX to_id (to_id),
    INDEX content (content(8))
)
    COMMENT '消息'
    CHARSET 'utf8';

DROP TABLE IF EXISTS contact;
CREATE TABLE contact
(
    relation_id     BIGINT PRIMARY KEY AUTO_INCREMENT
        COMMENT '主键ID',
    my_user_id      INT       NOT NULL
        COMMENT '第一人称用户ID',
    contact_user_id INT       NOT NULL
        COMMENT '第三人称用户ID',
    remark_name     VARCHAR(16) COMMENT '联系人备注',
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        COMMENT '创建时间',
    UNIQUE unique_my_user_id_contact_user_id (my_user_id, contact_user_id)
        COMMENT '唯一索引(第一人称用户ID=>第三人称用户ID)',
    INDEX contact_user_id (contact_user_id)
)
    COMMENT '联系人'
    CHARSET 'utf8'
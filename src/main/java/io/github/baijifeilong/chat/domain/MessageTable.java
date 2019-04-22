package io.github.baijifeilong.chat.domain;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/18 下午5:40
 * <p>
 * 消息表
 */
@Data
public class MessageTable {

    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "type")
    private Type type;

    @Column(name = "from_id")
    private Integer fromId;

    @Column(name = "to_id")
    private Long toId;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum Type {
        CHAT,
        GROUPCHAT
    }
}

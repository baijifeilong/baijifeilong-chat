package io.github.baijifeilong.chat.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/17 下午2:24
 * <p>
 * 消息模型
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class Message extends MessageTable {

    @Column(name = "name")
    private String fromNickname;
}

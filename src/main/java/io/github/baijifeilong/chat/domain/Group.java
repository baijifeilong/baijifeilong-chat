package io.github.baijifeilong.chat.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/16 下午9:55
 * <p>
 * 群组模型
 */
@Data
public class Group {

    private Integer groupId;

    private Integer ownerId;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}

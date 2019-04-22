package io.github.baijifeilong.chat.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/17 下午2:22
 * <p>
 * 群组群成员关系模型
 */
@Data
public class GroupUserRelation {

    private Integer relationId;

    private Integer groupId;

    private Integer userId;

    private LocalDateTime createdAt;
}

package io.github.baijifeilong.chat.domain;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/17 下午8:35
 * <p>
 * 联系人表
 */
@Data
public class ContactTable {

    @Column(name = "relation_id")
    private Long relationId;

    @Column(name = "my_user_id")
    private Integer myUserId;

    @Column(name = "contact_user_id")
    private Integer contactUserId;

    @Column(name = "remark_name")
    private String remarkName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

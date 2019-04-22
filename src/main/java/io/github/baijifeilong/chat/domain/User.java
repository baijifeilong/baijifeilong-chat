package io.github.baijifeilong.chat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/16 下午1:21
 * <p>
 * 用户模型
 */
@Data
public class User {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "encrypted_password")
    @JsonIgnore
    private String encryptedPassword;

    @Column(name = "name")
    private String nickname;
}

package io.github.baijifeilong.chat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import java.util.Optional;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/17 下午9:23
 * <p>
 * 联系人模型
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Contact extends ContactTable {

    @Column(name = "name")
    private String nickname;

    @Column(name = "mobile")
    private String mobile;

    @SuppressWarnings("unused")
    @JsonProperty
    public String remarkNameOrNickname() {
        return Optional.ofNullable(getRemarkName()).orElse(nickname);
    }
}

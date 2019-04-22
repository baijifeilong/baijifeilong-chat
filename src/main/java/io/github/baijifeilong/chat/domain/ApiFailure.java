package io.github.baijifeilong.chat.domain;

import lombok.Getter;

/**
 * Created by BaiJiFeiLong@gmail.com at 2018/12/28 上午11:13
 * <p>
 * 接口失败响应
 */
@Getter
public class ApiFailure {

    private int code;
    private String message;

    private ApiFailure(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ApiFailure of(int code, String message) {
        return new ApiFailure(code, message);
    }
}

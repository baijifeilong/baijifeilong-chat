package io.github.baijifeilong.chat.exception;

import lombok.Getter;

/**
 * Created by BaiJiFeiLong@gmail.com at 2018/12/28 上午11:34
 * <p>
 * 业务异常枚举
 */
@Getter
public enum BizExceptionEnum {
    FUZZY(10000, "NONE"),
    REGISTER_FAILED(10001, "注册失败"),
    USER_NOT_FOUND(10002, "用户未找到"),
    MOBILE_FORMAT_ILLEGAL(10003, "手机号格式错误"),
    PLACEHOLDER(-1, "PLACEHOLDER");

    private int code;
    private String message;

    BizExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

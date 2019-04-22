package io.github.baijifeilong.chat.exception;

import lombok.Getter;

/**
 * Created by BaiJiFeiLong@gmail.com at 2018/12/10 下午5:17
 * <p>
 * 业务异常基类
 */
@Getter
public class BizException extends RuntimeException {

    private int code;

    public BizException(BizExceptionEnum bee) {
        super(bee.getMessage());
        this.code = bee.getCode();
    }

    public BizException(BizExceptionEnum bee, String message, Object... args) {
        super(String.format(message, args));
        this.code = bee.getCode();
    }

    public BizException(BizExceptionEnum bee, String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
        this.code = bee.getCode();
    }
}

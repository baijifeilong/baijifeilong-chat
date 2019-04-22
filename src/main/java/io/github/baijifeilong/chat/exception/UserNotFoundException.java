package io.github.baijifeilong.chat.exception;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/16 下午1:41
 * <p>
 * 用户不存在异常
 */
public class UserNotFoundException extends BizException {
    public UserNotFoundException() {
        super(BizExceptionEnum.USER_NOT_FOUND);
    }

    public UserNotFoundException(String message, Object... args) {
        super(BizExceptionEnum.USER_NOT_FOUND, message, args);
    }

    public UserNotFoundException(String message, Throwable cause, Object... args) {
        super(BizExceptionEnum.USER_NOT_FOUND, message, cause, args);
    }
}

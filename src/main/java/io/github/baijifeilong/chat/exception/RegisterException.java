package io.github.baijifeilong.chat.exception;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/16 上午11:55
 * <p>
 * 注册异常
 */
@SuppressWarnings("unused")
public class RegisterException extends BizException {
    public RegisterException() {
        super(BizExceptionEnum.REGISTER_FAILED);
    }

    public RegisterException(String message, Object... args) {
        super(BizExceptionEnum.REGISTER_FAILED, message, args);
    }

    public RegisterException(String message, Throwable cause, Object... args) {
        super(BizExceptionEnum.REGISTER_FAILED, message, cause, args);
    }
}

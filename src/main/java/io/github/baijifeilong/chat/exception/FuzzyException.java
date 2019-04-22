package io.github.baijifeilong.chat.exception;

/**
 * Created by BaiJiFeiLong@gmail.com at 2018/12/28 上午11:32
 * <p>
 * 模糊异常/不明确的异常
 */
@SuppressWarnings("unused")
public class FuzzyException extends BizException {
    public FuzzyException() {
        super(BizExceptionEnum.FUZZY);
    }

    public FuzzyException(String message, Object... args) {
        super(BizExceptionEnum.FUZZY, message, args);
    }

    public FuzzyException(String message, Throwable cause, Object... args) {
        super(BizExceptionEnum.FUZZY, message, cause, args);
    }
}

package io.github.baijifeilong.chat.config;

import io.github.baijifeilong.chat.domain.ApiFailure;
import io.github.baijifeilong.chat.exception.BizException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/16 下午2:34
 * <p>
 * SpringMVC全局异常处理
 */
@RestControllerAdvice
public class MyControllerAdvice {

    /**
     * 未知异常处理
     *
     * @param e .
     * @return .
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiFailure processException(Throwable e) {
        return ApiFailure.of(500, e.getLocalizedMessage());
    }

    /**
     * 404异常处理
     *
     * @param e .
     * @return .
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiFailure processNotFoundException(NoHandlerFoundException e) {
        return ApiFailure.of(404, String.format("页面(%s)未找到", e.getRequestURL()));
    }

    /**
     * 业务异常处理
     *
     * @param e .
     * @return .
     */
    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiFailure processBizException(BizException e) {
        return ApiFailure.of(e.getCode(), e.getLocalizedMessage());
    }
}

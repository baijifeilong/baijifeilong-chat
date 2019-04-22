package io.github.baijifeilong.chat.domain;

import lombok.Getter;
import org.springframework.data.domain.Page;

/**
 * Created by BaiJiFeiLong@gmail.com at 2018/12/28 上午11:07
 * <p>
 * 接口成功响应
 */
@Getter
public class ApiSuccess<T> {

    private Object data;

    private ApiSuccess(T data) {
        this.data = data;
    }

    public static <T> ApiSuccess<T> of(T t) {
        return new ApiSuccess<>(t);
    }

    public static <T> ApiSuccess<ApiPage<T>> ofPage(Page<T> t) {
        return new ApiSuccess<>(ApiPage.of(t));
    }
}
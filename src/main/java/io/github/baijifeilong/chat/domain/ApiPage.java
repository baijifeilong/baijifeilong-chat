package io.github.baijifeilong.chat.domain;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by BaiJiFeiLong@gmail.com at 2018/12/28 上午11:16
 * <p>
 * 接口分页
 */
@Getter
public class ApiPage<T> {

    private int pageIndex;
    private int itemsPerPage;
    private int totalItems;
    private int totalPages;
    private int currentItemCount;
    private List<T> items;

    private ApiPage(int pageIndex, int itemsPerPage, int totalItems, int totalPages, int currentItemCount, List<T> items) {
        this.pageIndex = pageIndex;
        this.itemsPerPage = itemsPerPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentItemCount = currentItemCount;
        this.items = items;
    }

    public static <T> ApiPage<T> of(Page<T> page) {
        return new ApiPage<>(
                page.getNumber() + 1,
                page.getSize(),
                (int) page.getTotalElements(),
                page.getTotalPages(),
                page.getContent().size(),
                page.getContent()
        );
    }
}

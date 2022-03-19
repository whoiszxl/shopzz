package com.whoiszxl.zero.utils;

import com.whoiszxl.zero.bean.MyPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * jpa工具類
 */
public class JpaUtils {

    /**
     * 获取排序参数
     * @param sortCode 升序or降序  1:升序，-1:降序
     * @param column 列名
     * @return
     */
    public static Sort getSort(Integer sortCode, String column) {
        Sort.Direction direction = sortCode == 1 ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(direction, column);
    }

    /**
     * 获取分页对象
     * @param number 页码
     * @param size 每页数
     * @param sort 排序参数
     * @return
     */
    public static Pageable getPageable(int number, int size, Sort sort) {
        return sort == null ? PageRequest.of(number, size) : PageRequest.of(number, size, sort);
    }

    /**
     * JPA Page 转 自定义MyPage
     * @param page
     * @return
     */
    public static MyPage convertMyPage(Page page) {
        MyPage myPage = new MyPage();
        myPage.setContent(page.getContent());
        myPage.setFirst(page.isFirst());
        myPage.setNumber(page.getNumber());
        myPage.setSize(page.getSize());
        myPage.setTotalPages(page.getTotalPages());
        return myPage;
    }
}

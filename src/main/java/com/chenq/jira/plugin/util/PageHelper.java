package com.chenq.jira.plugin.util;

import com.atlassian.jira.rest.api.pagination.PageBean;
import com.atlassian.jira.util.Page;
import com.atlassian.jira.util.PageRequest;
import com.atlassian.jira.util.PageRequests;
import com.atlassian.jira.util.Pages;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description
 * @Date 2019/5/13 13:03
 * @Created by chenq
 */
public class PageHelper {

    public static PageRequest request(int pageNum, int pageSize) {
        if (pageSize == 0) {
            // 默认一页显示10条
            pageSize = 10;
        }
        if (pageNum < 1) {
            pageNum = 1;
        }

        return PageRequests.request((long) ((pageNum - 1) * pageSize), pageSize);
    }

    /**
     * 如果是已经是可以序列化的bean，使用此方法
     */
    public static <T> PageBean<T> toBean(Page<T> page) {
        return toBean(page, Function.identity());
    }

    /**
     * 将page数据转换为bean
     *
     * @param page     page
     * @param function function
     * @return pageBean
     */
    public static <T, R> PageBean<R> toBean(Page<T> page, Function<T, R> function) {
        return PageBean.from(PageRequests.request(page.getStart(), page.getSize()), page).build(function);
    }

    /**
     * 将list转为page数据吧v
     *
     * @param list        集合数据
     * @param pageRequest 分页信息
     * @return 分页数据
     */
    public static <T> Page<T> listToPage(List<T> list, PageRequest pageRequest) {
        if (list == null) {
            list = new ArrayList<>();
        }

        return Pages.page(list.stream().skip(pageRequest.getStart()).limit(pageRequest.getLimit())
                .collect(Collectors.toList()), list.size(), pageRequest);
    }
}

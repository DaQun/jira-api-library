package com.github.daqun.jira.permission;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 视图类，可能是一个页面，也可能是一个panel
 * @Date 2019/4/12 10:57
 * @Created by chenq
 */
@Getter
@Setter
@Builder
public class ViewItem {
    // view的key
    private String key;
    // 视图名称
    private String name;
    // 视图访问的路径
    private String url;

    public ViewItem(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public ViewItem(String name) {
        this.name = name;
    }

    public ViewItem(String key, String name, String url) {
        this.key = key;
        this.name = name;
        this.url = url;
    }
}

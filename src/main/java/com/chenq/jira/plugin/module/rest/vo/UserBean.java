package com.chenq.jira.plugin.module.rest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 2020/6/14 20:35
 * Created by chenq
 */
@Data
@AllArgsConstructor
public class UserBean {
    private String name;
    private String address;
    private int age;
    private String groupNo;
}

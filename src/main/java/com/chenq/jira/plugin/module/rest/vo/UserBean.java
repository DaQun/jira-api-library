package com.chenq.jira.plugin.module.rest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 2020/6/14 20:35
 * Created by chenq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType
public class UserBean {
    private String name;
    private String address;
    private Integer age;
    private String groupNo;
}

package com.chenq.jira.plugin.module.rest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 用作dto和vo
 * 2020/6/14 20:21
 * Created by chenq
 */
@XmlAccessorType
@Data
@AllArgsConstructor
public class GroupBean {
    /**
     * javax.validation下的验证注解，
     * 其他注解可到此包下查找需要的
     */
    @NotBlank(message = "groupNo 不能为空")
    private String groupNo;
    private String groupName;
    private String parent;
    private String type;
    private Boolean deleted;
}

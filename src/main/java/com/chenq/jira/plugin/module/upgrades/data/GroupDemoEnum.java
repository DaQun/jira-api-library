package com.chenq.jira.plugin.module.upgrades.data;

import com.chenq.jira.plugin.api.constants.GroupTypeEnum;
import com.chenq.jira.plugin.module.rest.vo.GroupBean;
import lombok.Getter;

/**
 * 部门示例数据 枚举类
 * 2020/6/14 20:17
 * Created by chenq
 */
public enum GroupDemoEnum {
    GROUP_C_1(new GroupBean("001", "阿里巴巴", null, GroupTypeEnum.COMPANY.name(), false)),
    GROUP_D_1(new GroupBean("002", "销售部", "001", GroupTypeEnum.DEPARTMENT.name(), false)),
    GROUP_D_2(new GroupBean("003", "研发部", "001", GroupTypeEnum.DEPARTMENT.name(), false)),

    GROUP_C_2(new GroupBean("004", "腾讯", null, GroupTypeEnum.COMPANY.name(), false)),
    GROUP_C_3(new GroupBean("005", "腾讯上海分部", null, GroupTypeEnum.COMPANY.name(), false)),
    GROUP_D_3(new GroupBean("006", "销售部", "004", GroupTypeEnum.DEPARTMENT.name(), false));

    @Getter
    private GroupBean groupBean;

    GroupDemoEnum(GroupBean groupBean) {
        this.groupBean = groupBean;
    }
}

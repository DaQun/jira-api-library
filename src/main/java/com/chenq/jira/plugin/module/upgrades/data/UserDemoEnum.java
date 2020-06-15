package com.chenq.jira.plugin.module.upgrades.data;

import com.chenq.jira.plugin.module.rest.vo.UserBean;
import lombok.Getter;

/**
 * 2020/6/14 20:35
 * Created by chenq
 */
public enum UserDemoEnum {
    USER_1(new UserBean("张三丰", "上海市徐汇区", 28, GroupDemoEnum.GROUP_C_1.getGroupBean().getGroupNo())),
    USER_2(new UserBean("皇太极", "北京市朝阳区", 33, GroupDemoEnum.GROUP_D_1.getGroupBean().getGroupNo())),
    USER_3(new UserBean("唐三藏", "女儿国", 29, GroupDemoEnum.GROUP_D_1.getGroupBean().getGroupNo())),
    USER_4(new UserBean("猪悟能", "高老庄", 330, GroupDemoEnum.GROUP_D_3.getGroupBean().getGroupNo())),
    USER_5(new UserBean("如来", "西天", 999, GroupDemoEnum.GROUP_C_3.getGroupBean().getGroupNo()));
    @Getter
    private UserBean userBean;

    UserDemoEnum(UserBean userBean) {
        this.userBean = userBean;
    }
}

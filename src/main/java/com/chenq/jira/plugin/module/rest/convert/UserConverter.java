package com.chenq.jira.plugin.module.rest.convert;

import com.chenq.jira.plugin.api.ao.entity.UserEntity;
import com.chenq.jira.plugin.module.rest.vo.UserBean;

/**
 * 2020/7/14 20:30
 * Created by chenq
 */
public class UserConverter {

    /**
     * 将数据库实体转为前台vo
     * @param userEntity user实体
     * @return userVO
     */
    public static final UserBean convert(UserEntity userEntity) {
        UserBean userBean = new UserBean();
        userBean.setAddress(userEntity.getAddress());
        userBean.setAge(userEntity.getAge());
        userBean.setGroupNo(userEntity.getGroup().getGroupNo());
        userBean.setName(userEntity.getName());

        return userBean;
    }

}

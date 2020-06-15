package com.chenq.jira.plugin.api.ao.entity;


import net.java.ao.Entity;

/**
 * @Classname ProductEntity
 * @Description
 * @Date 2019/3/2 16:14
 * @Created by chenq
 */
public interface ProductEntity extends Entity {
    UserEntity getUser();
    enum COLUMN {
        USER_ID ,ID;
    }
}

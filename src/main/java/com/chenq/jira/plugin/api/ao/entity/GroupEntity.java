package com.chenq.jira.plugin.api.ao.entity;

import com.chenq.jira.plugin.api.constants.GroupTypeEnum;
import com.github.daqun.jira.ao.column.BaseColumn;
import com.github.daqun.jira.ao.column.DeleteColumn;
import net.java.ao.OneToMany;
import net.java.ao.schema.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * 2019/3/25 10:41
 * created by chenq
 */
// 多字段索引
@Indexes({
        @Index(name = "first", methodNames = {"getGroupNo", "getGroupName"}),
        @Index(name = "second", methodNames = {"getParent", "getGroupName"}),
})
public interface GroupEntity extends BaseColumn, DeleteColumn {

    // 单字段索引
    @NotNull
    @Indexed
    @Unique
    String getGroupNo();
    void setGroupNo(String groupNo);

    // 限制字段长度
    @StringLength(200)
    String getGroupName();
    void setGroupName(String groupName);

    String getParent();
    void setParent(String parent);

    // 字段可以设置枚举类型，数据库对应的字段类型其实是varchar
    GroupTypeEnum getType();
    void setType(GroupTypeEnum type);

    @OneToMany
    UserEntity[] getUsers();

    enum COLUMN {
        PARENT ,TYPE ,GROUP_NO  ,GROUP_NAME ,ID;
    }
}
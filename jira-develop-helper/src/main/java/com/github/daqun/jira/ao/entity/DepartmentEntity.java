package com.github.daqun.jira.ao.entity;

import net.java.ao.Entity;
import net.java.ao.schema.Index;
import net.java.ao.schema.Indexed;
import net.java.ao.schema.Indexes;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

//import org.hibernate.validator.constraints.NotBlank;
//import org.hibernate.validator.constraints.Range;

/**
 * @Classname DepartmentEntity
 * @Description
 * @Date 2019/3/25 10:41
 * @Created by chenq
 */
@Indexes({
        @Index(name = "first", methodNames = {"getGroupNo", "getGroupName"}),
        @Index(name = "second", methodNames = {"getParent", "getGroupName"}),
})
public interface DepartmentEntity extends Entity {
//    @NotBlank(message = "groupNo 不能为空")
    String getGroupNo();

    void setGroupNo(String groupNo);
//    @NotBlank(message = "groupName 不能为空")
    String getGroupName();

    void setGroupName(String groupName);
    @Size(min = 1,max = 9, message = "数值在1-9")
    String getParent();

    void setParent(String parent);
    @Max(value = 4L, message = "不能大于4")
    int getStatus();

    void setStatus(int status);

    /** type = 0 部门
     * type = 1 团队*/
//    @Range(min = 1, max = 9)
    @Indexed
    int getType();

    void setType(int type);

    enum COLUMN {
        PARENT ,TYPE ,GROUP_NO ,STATUS ,GROUP_NAME ,ID;
    }
}
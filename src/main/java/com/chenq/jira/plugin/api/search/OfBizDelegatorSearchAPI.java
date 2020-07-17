package com.chenq.jira.plugin.api.search;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.ofbiz.OfBizDelegator;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.google.common.collect.ImmutableList;
import org.ofbiz.core.entity.EntityCondition;
import org.ofbiz.core.entity.EntityExpr;
import org.ofbiz.core.entity.EntityOperator;
import org.ofbiz.core.entity.GenericValue;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

import static org.ofbiz.core.entity.EntityOperator.EQUALS;

/**
 * jira的系统表时用OfBiz实现的，所以jira里的系统表数据可以使用OfBizDelegator搜索
 * 2020/7/10 12:01
 * Created by chenq
 */
@Component
public class OfBizDelegatorSearchAPI {
    @ComponentImport
    private OfBizDelegator ofBizDelegator;

    @Inject
    public OfBizDelegatorSearchAPI(OfBizDelegator ofBizDelegator) {
        this.ofBizDelegator = ofBizDelegator;
    }

    /**
     * 查询jira系统中有哪些多选用户类型的自定义字段
     */
    public List<GenericValue> getMultiUserPicker() {
        return ofBizDelegator.findByField("CustomField", "customfieldtypekey", "com.atlassian.jira.plugin.system.customfieldtypes:multiuserpicker");
    }


    /**
     * 查询issue在指定日期范围内的所有日志
     * @param issue issue
     * @param start 开始时间
     * @param end 结束时间
     */
    public List<GenericValue> searchWorklogs(Issue issue, Timestamp start, Timestamp end) {
        EntityCondition startExpr = new EntityExpr("startdate", EntityOperator.GREATER_THAN_EQUAL_TO, start);
        EntityCondition endExpr = new EntityExpr("startdate", EntityOperator.LESS_THAN, end);

        final ImmutableList<EntityCondition> entityConditions = ImmutableList.of(
                new EntityExpr("issue", EQUALS, issue.getId()),
                startExpr,
                endExpr);
        return ofBizDelegator.findByAnd("Worklog", entityConditions);
    }


}

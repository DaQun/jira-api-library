package com.github.daqun.jira.ao;

import com.google.common.collect.Lists;
import net.java.ao.DBParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname DBParamBuilder
 * @Description DBParam的封装
 * @Date 2019/3/21 17:57
 * @Created by chenq
 */
public class DBParamBuilder {

    public DBParamBuilder() {
        this.dbParamList = new ArrayList<>();
    }

    public DBParamBuilder(List<DBParam> dbParamList) {
        this.dbParamList = dbParamList;
    }

    public DBParamBuilder(DBParam... dbParams) {
        this.dbParamList = Lists.newArrayList(dbParams);
    }

    private List<DBParam> dbParamList;

    public DBParamBuilder append(DBParam dbParam) {
        this.dbParamList.add(dbParam);
        return this;
    }

    public DBParamBuilder append(String field, Object value) {
        this.dbParamList.add(new DBParam(field, value));
        return this;
    }

    public DBParamBuilder append(Enum field, Object value) {
        this.dbParamList.add(new DBParam(field.name(), value));
        return this;
    }

    public DBParam[] build() {
        return dbParamList.toArray(new DBParam[0]);
    }
}

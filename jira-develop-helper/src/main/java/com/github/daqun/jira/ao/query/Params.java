package com.github.daqun.jira.ao.query;

/**
 * @Classname Params 未完善
 * @Description 一般不会有很复杂的查询，直接写字符串就好了，使用这个类反而阅读增加难度
 * @Date 2019/3/3 0:17
 * @Created by chenq
 */
public class Params {

    private StringBuilder sb = new StringBuilder();

    public Params equal(Object fieldName, int value) {
        sb.append(fieldName).append(" = ").append(value);
        return this;
    }


    public Params equal(Object fieldName, String value) {
        sb.append(fieldName).append(" = ").append("'").append(value).append("'");
        return this;
    }

    public Params gt(Object fieldName, int value) {
        sb.append(fieldName).append(" > ").append(value);
        return this;
    }

    public Params lt(Object fieldName, int value) {
        sb.append(fieldName).append(" < ").append(value);
        return this;
    }

    public Params and() {
        sb.append(" AND ");
        return this;
    }

    public Params or() {
        sb.append(" OR ");
        return this;
    }

    public String build() {
        return sb.toString();
    }

}

package com.github.daqun.jira.ao;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.util.Page;
import com.atlassian.jira.util.PageRequest;
import com.atlassian.jira.util.Pages;
import com.github.daqun.jira.ao.column.BaseColumn;
import com.github.daqun.jira.ao.column.DeleteColumn;
import com.github.daqun.jira.ao.column.UpdateColumn;
import com.github.daqun.jira.core.DsdApp;
import com.google.common.collect.Lists;
import net.java.ao.DBParam;
import net.java.ao.Entity;
import net.java.ao.EntityStreamCallback;
import net.java.ao.Query;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkState;

/**
 * @Classname EnhanceActiveObject
 * @Description ao的增强类，声明了ao的常用方法，如果需要使用一些不常用方法，使用getAO方法，获取实际的ao
 * @Date 2019/3/2 18:26
 * @Created by chenq
 */
public class EnhanceActiveObject {
    private ActiveObjects ao;

    public EnhanceActiveObject(ActiveObjects activeObjects) {
        this.ao = activeObjects;
    }

    private static final Map<Class<? extends Entity>, Boolean> DELETE_ENTITY_MAP = new HashMap();

    /**
     * 是否继承了DeleteColumn，用来做逻辑删除判断
     * @param clazz entity类
     * @return
     */
    private boolean hasDeleteColumn(Class<? extends Entity> clazz) {
        return DELETE_ENTITY_MAP.computeIfAbsent(clazz, DeleteColumn.class::isAssignableFrom);
    }

    private boolean hasBaseColumn(Class clazz) {
        return BaseColumn.class.isAssignableFrom(clazz);
    }

    public ActiveObjects getAO() {
        return this.ao;
    }

    public <T extends Entity> T[] get(Class<T> aClass, Integer[] ks) {
        return ao.get(aClass, ks);
    }

    /**
     * 逻辑
     * @param aClass
     * @param ks
     * @param <T>
     * @return
     */
    public <T extends DeleteColumn> T[] getLogical(Class<T> aClass, Integer[] ks) {
        T[] ts = ao.get(aClass, ks);
        return (T[]) Arrays.stream(ts).filter(e -> e.isDeleted()).toArray();
    }


    public <T extends Entity> T get(Class<T> aClass, Integer k) {
        return ao.get(aClass, k);
    }
    /**
     * 逻辑
     */
    public <T extends Entity> T getLogical(Class<T> aClass, Integer k) {
        T t = ao.get(aClass, k);
        // 如果有删除字段，判断此时字段值是否是已删除
        if (hasDeleteColumn(aClass) && ((DeleteColumn) t).isDeleted()) {
            return null;
        }
        return t;
    }

    /**
     * 返回实体之前，预设两个参数，供更新
     */
    public <T extends UpdateColumn> T getForUpdate(Class<T> aClass, Integer id) {
        T t = ao.get(aClass, id);
        if (t != null) {
            t.setModifyTime(new Date());
            t.setModifier(DsdApp.getLoggedInUser().getKey());
        }

        return t;
    }

    public <T extends Entity> T create(Class<T> aClass, DBParam... dbParams) {
        return ao.create(aClass, dbParams);
    }

    public <T extends Entity> T create(Class<T> aClass, Map<String, Object> map) {
        return ao.create(aClass, map);
    }

    public void delete(Entity... entities) {
        ao.delete(entities);
    }

    /**
     * 逻辑删除
     */
    public <T extends DeleteColumn> void deleteLogical(T t) {
        t.setDeleted(true);
        t.save();
    }

    public <T extends Entity> T[] find(Class<T> aClass) {
        return ao.find(aClass);
    }

    /**
     * 逻辑查询
     */
    public <T extends Entity> T[] findLogical(Class<T> aClass) {
        checkState(hasDeleteColumn(aClass));
        Query query = Query.select().where(DeleteColumn.COLUMN.DELETED + " = ?", false);

        return ao.find(aClass, query);
    }

    public <T extends Entity> T[] find(Class<T> aClass, String s, Object... objects) {
        return ao.find(aClass, s, objects);
    }
    /**
     * 逻辑查询
     */
    public <T extends Entity> T[] findLogical(Class<T> aClass, String s, Object... objects) {
        checkState(hasDeleteColumn(aClass));

        return ao.find(aClass, wrapLogicalDelete(s), objects, false);
    }

    private String wrapLogicalDelete(String query) {
        return " ( " + query + " ) AND " + DELETED_WHERE;
    }
    private static final String DELETED_WHERE = " " + DeleteColumn.COLUMN.DELETED + " = ?";

    public <T extends Entity> T[] find(Class<T> aClass, Query query) {
        return ao.find(aClass, query);
    }

// 本来想用spring-web-data下的page，但是安装之后找不到类，还是用jira原生的page类吧
//    public <T extends Entity> Page<T> find(Class<T> aClass, Query query, Pageable pageable) {
//        // 先查询count，否则count和limit混用会出现问题
//        long total = ao.count(aClass, query);
//
//        if (pageable != null) {
//            query.limit(pageable.getPageSize()).offset(pageable.getOffset());
//        }
//
//        T[] ts = ao.find(aClass, query);
//
//        return new PageImpl(Lists.newArrayList(ts), pageable, total);
//    }

    public <T extends Entity> Page<T> find(Class<T> aClass, Query query, PageRequest pageRequest) {
        // 先查询count，否则count和limit混用会出现问题
        long total = ao.count(aClass, query);

        if (pageRequest != null) {
            query.limit(pageRequest.getLimit()).offset(pageRequest.getLimit());
        }

        T[] ts = ao.find(aClass, query);

        return  Pages.page(Lists.newArrayList(ts), total, pageRequest);
    }
    /**
     * 逻辑查询
     */
    public <T extends Entity> T[] findLogical(Class<T> aClass, Query query) {
        checkState(hasDeleteColumn(aClass));
        this.logicalQueryWhere(query);

        return ao.find(aClass, query);
    }

    public <T extends Entity> void stream(Class<T> aClass, EntityStreamCallback<T, Integer> entityStreamCallback) {
        ao.stream(aClass, entityStreamCallback);
    }

    public <T extends Entity> void stream(Class<T> aClass, Query query,
            EntityStreamCallback<T, Integer> entityStreamCallback) {
        ao.stream(aClass, query, entityStreamCallback);
    }

    public int count(Class<? extends Entity> aClass) {
        return ao.count(aClass);
    }
    /**
     * 逻辑count
     */
    public int countLogical(Class<? extends Entity> aClass) {
        checkState(hasDeleteColumn(aClass));

        return ao.count(aClass, Query.select().where(DeleteColumn.COLUMN.DELETED + " = ?", false));
    }

    public  int count(Class<? extends Entity> aClass, String s, Object... objects) {
        return ao.count(aClass, s, objects);
    }
    /**
     * 逻辑count
     */
    public  int countLogical(Class<? extends Entity> aClass, String s, Object... objects) {
        checkState(hasDeleteColumn(aClass));

        return ao.count(aClass, wrapLogicalDelete(s), objects, false);
    }

    public int count(Class<? extends Entity> aClass, Query query) {
        return ao.count(aClass, query);
    }
    /**
     * 逻辑count
     */
    public int countLogical(Class<? extends Entity> aClass, Query query) {
        checkState(hasDeleteColumn(aClass));
        this.logicalQueryWhere(query);

        return ao.count(aClass, query);
    }

    private void logicalQueryWhere(Query query) {
        String whereClause = query.getWhereClause();
        // 查询条件为空时
        if (StringUtils.isBlank(whereClause)) {
            query.where(DELETED_WHERE, false);
        } else if (!whereClause.contains(DELETED_WHERE)){ // 如果已经有了就不加了
            query.where(wrapLogicalDelete(whereClause), query.getWhereParams(), false);
        }
    }

    /**
     * 查找列的最大值
     */
    public <T extends Entity> T maxColumn(Class<T> aClass, String column) {
        return mmColumn(aClass, column, "DESC");
    }

    /**
     * ID的最大值得entity
     */
    public <T extends Entity> T maxID(Class<T> aclass) {
        return maxColumn(aclass, "ID");
    }

    public <T extends Entity> T minColumn(Class<T> aClass, String column) {
        return mmColumn(aClass, column, "ASC");
    }

    /**
     * 查询列的最大值或最小值，一般用来查询数字类型的值，比如ID，其他类型的使用之前先测试确认
     * @param aClass 实体类
     * @param column 字段
     * @param order 排序方式 desc、asc
     * @return 当表中没有数据时，返回null
     */
    private <T extends Entity> T mmColumn(Class<T> aClass, String column, String order) {
        T[] ts = ao.find(aClass, Query.select().limit(1).order(column + " " + order));
        return ts.length > 0 ? ts[0] : null;
    }
}

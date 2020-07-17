package com.github.daqun.jira.ao.service;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.util.Page;
import com.atlassian.jira.util.PageRequest;
import com.atlassian.jira.util.Pages;
import com.github.daqun.jira.ao.EnhanceActiveObject;
import com.github.daqun.jira.ao.column.BaseColumn;
import com.github.daqun.jira.ao.column.DeleteColumn;
import com.github.daqun.jira.util.BeanUtil;
import com.google.common.collect.Lists;
import net.java.ao.DBParam;
import net.java.ao.Entity;
import net.java.ao.Query;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @Classname BaseService
 * @Description
 * @Date 2019/2/27 17:21
 * @Created by chenq
 */
public abstract class BaseService<T extends Entity> implements IDaoService<T>, ILogicalDeleteService<T> {

//    protected final ActiveObjects ao;

    protected  final EnhanceActiveObject ao;

    public BaseService(ActiveObjects ao) {
        this.ao = new EnhanceActiveObject(ao);
    }

    private Class<T> clazz;
    private boolean hasDeleteColumn = false;
    private boolean hasBaseColumn = false;

    @Override
    public void delete(int id) {
        this.delete((T) ao.get(thisType(), id));
    }

    @Override
    public void delete(T t) {
        ao.delete(t);
    }

    @Override
    public void deleteLogical(T t) {
        if (hasDeleteColumn) {
            ao.deleteLogical(((DeleteColumn) t));
        } else {
            System.err.println("The entity is not implements DeleteColumn,can not logical delete");
        }
    }

    @Override
    public void deleteLogical(int id) {
        this.deleteLogical(ao.get(thisType(), id));
    }

    @Override
    public T get(int id) {
        return ao.get(thisType(), id);
    }

    @Override
    public T getLogical(int id) {
        return ao.getLogical(thisType(), id);
    }

    @Override
    public List<T> all() {
        return Lists.newArrayList(ao.find(thisType()));
    }

    @Override
    public List<T> allLogical() {
        return Lists.newArrayList(ao.findLogical(thisType()));
    }

    @Override
    public Page<T> query(Query query, PageRequest page) {
        if (page != null) {
            query.limit(page.getLimit()).offset((int) page.getStart());
        }

        T[] ts = ao.find(thisType(), query);
        int total = ao.count(thisType(), query);
        return Pages.page(Lists.newArrayList(ts), total, page);
    }


    @Override
    public T create(Map<String, Object> map) {
        return ao.create(thisType(), map);
    }

    @Override
    public T create(DBParam... dbParams) {
        return ao.create(thisType(), dbParams);
    }

    @Override
    public Page<T> queryLogical(Query query, PageRequest page) {
        int total = ao.countLogical(thisType(), query);

        if (page != null) {
            query.limit(page.getLimit()).offset((int) page.getStart());
        }

        T[] ts = ao.findLogical(thisType(), query);
        return Pages.page(Lists.newArrayList(ts), total, page);
    }

    protected Consumer<T> updateColumnConsumer = t -> {
        if (t instanceof BaseColumn){
            ((BaseColumn) t).setModifier("modifier");
            ((BaseColumn) t).setModifyTime(new Date());
        }
    };

    /**
     * 更新并保存
     * @param entity
     * @param bean
     */
    public void updateAndSave(Entity entity, Object bean) {
        BeanUtil.copy(bean, entity);
        entity.save();
    }
    /**
     * 仅更新，后续操作之后再保存
     * @param entity
     * @param bean
     */
    public void update(Entity entity, Object bean) {
        BeanUtil.copy(bean, entity);
    }

    /*
     * 获得当前类的Class
     */
    private Class<T> thisType() {
        if (clazz == null) {
            //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
            Type type = this.getClass().getGenericSuperclass();
            ParameterizedType paramType = null;
            if (type instanceof ParameterizedType) {
                //注意此处type必须是有泛型参数
                paramType = (ParameterizedType) type;
            } else {
                try {
                    throw new Exception("not find ParameterizedType!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Type[] types = paramType.getActualTypeArguments();//返回表示此类型实际类型参数的 Type 对象的数组
            clazz = (Class<T>) types[0];
            hasDeleteColumn = DeleteColumn.class.isAssignableFrom(clazz);
            hasBaseColumn = BaseColumn.class.isAssignableFrom(clazz);
        }

        return clazz;
    }

    /**
     * 获取查询结果的第一条，如果数组length为0，返回null
     * @param entities 实体数组
     * @return 第一条记录，或者null
     */
    protected T getFirst(T[] entities) {
        return getFirst(entities, null);
    }

    protected T getFirst(T[] entities, T entity) {
        return entities.length > 0 ? entities[0] : entity;
    }


}

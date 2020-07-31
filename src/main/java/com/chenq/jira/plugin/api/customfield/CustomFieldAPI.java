package com.chenq.jira.plugin.api.customfield;

import com.atlassian.core.util.collection.EasyList;
import com.atlassian.jira.config.managedconfiguration.ConfigurationItemAccessLevel;
import com.atlassian.jira.config.managedconfiguration.ManagedConfigurationItem;
import com.atlassian.jira.config.managedconfiguration.ManagedConfigurationItemBuilder;
import com.atlassian.jira.config.managedconfiguration.ManagedConfigurationItemService;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.context.GlobalIssueContext;
import com.atlassian.jira.issue.customfields.CustomFieldSearcher;
import com.atlassian.jira.issue.customfields.CustomFieldType;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.screen.FieldScreen;
import com.atlassian.jira.issue.fields.screen.FieldScreenManager;
import com.atlassian.jira.issue.fields.screen.FieldScreenTab;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.jira.web.action.admin.customfields.CreateCustomField;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.ofbiz.core.entity.GenericEntityException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

/**
 * 2020/5/26 14:22
 * Created by chenq
 */
@Slf4j
@Component
public class CustomFieldAPI {
    @ComponentImport protected final CustomFieldManager customFieldManager;
    @ComponentImport protected final ManagedConfigurationItemService managedConfigurationItemService;
    @ComponentImport protected final FieldScreenManager fieldScreenManager;

    @Inject
    public CustomFieldAPI(CustomFieldManager customFieldManager, ManagedConfigurationItemService managedConfigurationItemService,
            FieldScreenManager fieldScreenManager) {
        this.customFieldManager = customFieldManager;
        this.managedConfigurationItemService = managedConfigurationItemService;
        this.fieldScreenManager = fieldScreenManager;
    }

    /**
     * 根据custom field字段名获取字段
     * com.github.daqun.jira 中自定义字段名是可以重复的,但是一般我们在系统中都不会定义重复的自定义字段名，
     * 所以使用弃用的#getCustomFieldObjectByName方法完全没问题（其实它内部调用的也是#getCustomFieldObjectsByName方法）
     *
     * @return CustomField
     */
    public CustomField getCustomFieldByName(String customFieldName) {
        Collection<CustomField> values = customFieldManager.getCustomFieldObjectsByName(customFieldName);

        return values.stream().findFirst().orElse(null);
    }

    /**
     * 获取jira系统中所有的自定义字段类型（返回结果可参考同级目录下customfiled.json）
     */
    public List<CustomFieldType<?, ?>> getCustomFieldTypes() {
        return customFieldManager.getCustomFieldTypes();
    }

    /**
     * 锁定自定义字段
     */
    public void lockCustomField(CustomField customField) {
        ManagedConfigurationItem managedField = managedConfigurationItemService.getManagedCustomField(customField);
        ManagedConfigurationItemBuilder builder;
        if (managedField != null) {
            builder = ManagedConfigurationItemBuilder.builder(managedField);
            builder.setManaged(true);
            builder.setConfigurationItemAccessLevel(ConfigurationItemAccessLevel.LOCKED);
            managedField = builder.build();
            managedConfigurationItemService.updateManagedConfigurationItem(managedField);
        }
    }

    /**
     * 创建自定义字段
     * @param fieldName 自定义字段名称
     * @return 创建后的字段信息
     */
    public CustomField createCustomField(String fieldName){
        //判断此字段是否已存在，若字段存在则不创建
        List<CustomField> customFields = customFieldManager.getCustomFieldObjects();
        for(CustomField field : customFields){
            if(field.getName().equals(fieldName) && field.getCustomFieldType().getKey().equals("com.atlassian.jira.plugin.system.customfieldtypes:textfield")){
                return field;
            }
        }
        //创建字段
        String description = "通过api创建的文本类型的自定义字段";
        try {
            CustomFieldType cfType = customFieldManager.getCustomFieldType(CreateCustomField.FIELD_TYPE_PREFIX + "textfield");
            CustomFieldSearcher searcher = customFieldManager.getCustomFieldSearcher(CreateCustomField.FIELD_TYPE_PREFIX + "textsearcher");
            final CustomField customField = customFieldManager.createCustomField(fieldName, description, cfType, searcher,
                    Lists.newArrayList(GlobalIssueContext.getInstance()), Lists.newArrayList());
            associateCustomFieldWithScreen(customField, null);
            lockCustomField(customField);
            return customField;
        } catch (GenericEntityException e) {
            log.error("create customfield fail", e);
            return null;
        }
    }

    /**
     * 设置自定义字段显示的面板
     * @param customField 字段
     * @param screen 界面
     */
    public void associateCustomFieldWithScreen(final CustomField customField, FieldScreen screen) {
        if (screen == null) {
            screen = fieldScreenManager.getFieldScreen(FieldScreen.DEFAULT_SCREEN_ID);
        }
        if ((screen != null) && (screen.getTabs() != null) && !screen.getTabs().isEmpty()) {
            final FieldScreenTab tab = screen.getTab(0);
            tab.addFieldScreenLayoutItem(customField.getId());
        }
    }
}



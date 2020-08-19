package com.chenq.jira.plugin.api.customfield;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.event.type.EventDispatchOption;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.customfields.impl.CascadingSelectCFType;
import com.atlassian.jira.issue.customfields.manager.OptionsManager;
import com.atlassian.jira.issue.customfields.option.Option;
import com.atlassian.jira.issue.customfields.option.Options;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.atlassian.jira.issue.fields.config.FieldConfigScheme;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jira单选框自定义字段
 * 2020/5/26 14:21
 * Created by chenq
 */
@Component
public class SelectCFAPI{
    @ComponentImport
    private final OptionsManager optionsManager;

    @Inject
    public SelectCFAPI(OptionsManager optionsManager) {
        this.optionsManager = optionsManager;
    }

    /**
     * 获取单选列表类型的自定义字段的选项值
     */
    public Options getSelectOptions(CustomField customField) {
        List<FieldConfigScheme> configurationSchemes = customField.getConfigurationSchemes();

        if (!configurationSchemes.isEmpty()) {
            FieldConfigScheme sc = configurationSchemes.get(0);
            MultiMap configs = sc.getConfigsByConfig();
            if (configs != null && !configs.isEmpty()) {
                FieldConfig config = (FieldConfig) configs.keySet().iterator().next();
                return optionsManager.getOptions(config);
            }
        }

        return null;
    }

    /**
     * 获取单选列表类型的自定义字段的选项值
     * 此方法需要传一个issue
     */
    public Options getSelectOptions(CustomField customField, Issue issue) {
        FieldConfig relevantConfig = customField.getRelevantConfig(issue);
        return optionsManager.getOptions(relevantConfig);
    }

    /**
     * 更新级联下拉框的自定义字段
     * @param cascadingSelectCF 级联的自定义字段
     * @param issue issue
     * @param parentValue 父级下拉框的字符值
     * @param childValue 子级下拉框的字符值
     */
    public void updateCascadingSelectValue(CustomField cascadingSelectCF, MutableIssue issue, String parentValue, String childValue) {
        FieldConfig config = cascadingSelectCF.getRelevantConfig(issue);
        OptionsManager optionsManager = ComponentAccessor.getOptionsManager();
        Option firstOption = optionsManager.getOptions(config).stream().filter(e -> StringUtils.equals(e.toString(), parentValue)).findFirst().get();
        Option secondOption = optionsManager.findByParentId(firstOption.getOptionId()).stream().filter(e -> StringUtils.equals(e.toString(), childValue)).findFirst().get();
        Map<String, Option> optionMap = new HashMap<>();
        optionMap.put(CascadingSelectCFType.PARENT_KEY, firstOption);
        optionMap.put(CascadingSelectCFType.CHILD_KEY, secondOption);
        issue.setCustomFieldValue(cascadingSelectCF, optionMap);

        ComponentAccessor.getIssueManager().updateIssue(ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
                issue, EventDispatchOption.ISSUE_UPDATED, false);

    }

}

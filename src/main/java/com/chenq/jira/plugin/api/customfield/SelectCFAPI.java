package com.chenq.jira.plugin.api.customfield;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.customfields.manager.OptionsManager;
import com.atlassian.jira.issue.customfields.option.Options;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.atlassian.jira.issue.fields.config.FieldConfigScheme;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.apache.commons.collections.MultiMap;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

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

}

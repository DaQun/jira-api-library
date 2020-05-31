package com.chenq.jira.plugin.api.customfield;

import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.customfields.manager.OptionsManager;
import com.atlassian.jira.issue.customfields.option.Options;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.atlassian.jira.issue.fields.config.FieldConfigScheme;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.MultiMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * jira单选框自定义字段
 * 2020/5/26 14:21
 * Created by chenq
 */
@Component
public class SelectCFAPI extends CustomFieldAPI{
    @ComponentImport
    private final OptionsManager optionsManager;

    public SelectCFAPI(CustomFieldManager customFieldManager, OptionsManager optionsManager) {
        super(customFieldManager);
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

}

package com.chenq.jira.plugin.api.customfield;

import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collection;

/**
 * 2020/5/26 14:22
 * Created by chenq
 */
@Component
public class CustomFieldAPI {
    @ComponentImport
    protected final CustomFieldManager customFieldManager;

    @Inject
    public CustomFieldAPI(CustomFieldManager customFieldManager) {
        this.customFieldManager = customFieldManager;
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

}

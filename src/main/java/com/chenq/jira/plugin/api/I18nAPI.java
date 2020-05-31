package com.chenq.jira.plugin.api;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.util.I18nHelper;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import lombok.RequiredArgsConstructor;

/**
 * 国际化
 * 2020/4/17 17:33
 * Created by chenq
 */
@RequiredArgsConstructor
public class I18nAPI {

    @ComponentImport
    private final I18nHelper i18nHelper;

    /**
     * 可以通过jiraAuthenticationContext获取i18nHelper
     * @return i18nHelper
     */
    public I18nHelper getI18nHelper() {
        return ComponentAccessor.getJiraAuthenticationContext().getI18nHelper();
    }

    /**
     * 获取国际化文本
     *
     * @param key properties文件中的key
     * @return 国际化文本
     */
    public String getText(String key) {
        return i18nHelper.getText(key);
    }
}

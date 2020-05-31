package com.chenq.jira.plugin.api.license;

import com.atlassian.jira.license.LicenseCountService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import lombok.RequiredArgsConstructor;

/**
 * 2020/4/17 16:23
 * Created by chenq
 */
@RequiredArgsConstructor
public class LicenseAPI {
    @ComponentImport
    private final LicenseCountService licenseCountService;

    /**
     * 获取 Jira 许可的用户数
     * @return 如果返回-1，则是无限制
     */
    public int getTotalBillableUsers() {
        return licenseCountService.totalBillableUsers();
    }

}

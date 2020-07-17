package com.chenq.jira.plugin.api.search;

import com.atlassian.jira.bc.user.search.UserSearchParams;
import com.atlassian.jira.bc.user.search.UserSearchService;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.util.UserManager;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 2020/4/17 15:11
 * Created by chenq
 */
@RequiredArgsConstructor
public class UserSearchAPI {
    @ComponentImport private final UserSearchService userSearchService;
    @ComponentImport private final UserManager userManager;

    /**
     * 查询user<br/>
     * 这里的方法只是一个示例，没有入参，具体查询参数可以参考See {@link com.atlassian.jira.bc.user.search.UserSearchParams.Builder}
     */
    public List<ApplicationUser>  searchUser() {
        UserSearchParams.Builder searchParamsBuilder = UserSearchParams.builder()
                .sorted(true)
                .maxResults(20)
                .allowEmptyQuery(true)
                .filter(user -> {
                    ApplicationUser userName = userManager.getUserByName(user.getName());
                    return userName.getDisplayName().contains("admin");
                });

        // findUsers第一个参数
        // @param query：the query to search username, display name and email address
        return userSearchService.findUsers("", UserSearchParams.builder().build());
    }

}

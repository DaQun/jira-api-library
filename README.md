# jira-api-library（持续更新中。。。）

此项目是jira常用api的演示项目，目前包括
- custom field
- issue 增删改查
- license 
- permission
- plugin
- pluginSetting （jira提供的全局key value存储方式）
- project （项目的增删改查）
- search (jql查询操作)
- user (用户的常用操作)
- workflow (工作流方面)

项目下还有一个`jira-develop-helper`模块，封装了jira的一些常用操作，简化了jira开发代码，可以方便的操作jira api

运行此项目需要install `jira-develop-helper`到本地，后续也可以单独安装此模块，在开发插件时引用。

## jira知识点
项目类别和项目的关联表 `nodeassociation`
当前项目总数和今天创建了多少项目: `audit_log`

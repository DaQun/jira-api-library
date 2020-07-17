JIRA Agile（敏捷）是指 JIRA Software（非 JIRA Core）所具有的功能。如：Board（面板）、Sprint（冲刺）等。

最初，这些功能是通过一款名为 “Greenhopper” 的插件实现的。后来，该插件与 JIRA Core 合并成为 JIRA Software。

JAVA API 接口文档：https://docs.atlassian.com/jira-software/6.7.7 （接口文档版本很旧，仅供参考。没有新版本的文档。）

```xml
<project>
<!-- JIRA Agile 相关-->
        <dependency>
            <groupId>com.atlassian.jira.plugins</groupId>
            <artifactId>jira-greenhopper-plugin</artifactId>
            <version>${jira.version}</version>
            <scope>provided</scope>
        </dependency>
         <plugin>
                <groupId>com.atlassian.maven.plugins</groupId>
                <artifactId>jira-maven-plugin</artifactId>
                <version>${amps.version}</version>
                <extensions>true</extensions>
                <configuration>
                    <productVersion>${jira.version}</productVersion>
                    <productDataVersion>${jira.version}</productDataVersion>
 
                    <!-- 开发环境 JIRA 默认安装 JIRA Software -->
                    <applications>
                        <application>
                            <applicationKey>jira-software</applicationKey>
                            <version>${jira.version}</version>
                        </application>
                    </applications>
 
                    <enableQuickReload>true</enableQuickReload>
                    <!-- 禁止 sdk 向谷歌发送数据 -->
                    <allowGoogleTracking>false</allowGoogleTracking>
 
                    <instructions>
                        <!-- Add package import here -->
                        <Import-Package>
                            com.atlassian.jira.plugin.webfragment.conditions,
                            org.springframework.osgi.*;resolution:="optional",
                            org.eclipse.gemini.blueprint.*;resolution:="optional",
                            *
                        </Import-Package>
                        <!-- Ensure plugin is spring powered -->
                        <Spring-Context>*</Spring-Context>
                    </instructions>
                </configuration>
            </plugin>
</project>
```
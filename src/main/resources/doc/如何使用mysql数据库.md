# JIRA 插件开发 - 使用 mysql 数据库
## 1. JIRA 使用 mysql 5.7。
jira现在还不支持mysql8，最高支持到mysql5.7
## 2.在src/main/resources目录下，创建config文件夹，然后创建`dbconfig.xml`，文件内容如下
```xml
<?xml version="1.0" encoding="UTF-8"?>
 
<jira-database-config>
    <name>defaultDS</name>
    <delegator-name>default</delegator-name>
    <database-type>mysql</database-type>
    <!-- <schema-name>jiraschema_4_0_1</schema-name>-->
    <jndi-datasource>
        <jndi-name>java:comp/env/jdbc/JiraDS</jndi-name>
    </jndi-datasource>
</jira-database-config>
```
## 3. pom.xml 按照如下所示进行修改（数据库连接信息按照自己的实际情况填写）
```xml
<project>
<dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.22</version>
            <scope>runtime</scope>
        </dependency>
</dependencies>
 
    <build>
        <plugins>    
            <plugin>
                <groupId>com.atlassian.maven.plugins</groupId>
                <artifactId>jira-maven-plugin</artifactId>
                <version>${amps.version}</version>
                <extensions>true</extensions>
 
                <configuration>
                    <productVersion>${jira.version}</productVersion>
                    <productDataVersion>${jira.version}</productDataVersion>
                    <enableQuickReload>true</enableQuickReload>
                    <allowGoogleTracking>false</allowGoogleTracking>
                    <instructions>
                        <!-- Ensure plugin is spring powered -->
                        <Spring-Context>*</Spring-Context>
                    </instructions>
 
                    <instanceId>JIRA</instanceId>
                    <products>
                        <product>  
                            <!-- 此处必须是小写字母 jira-->       
                            <id>jira</id>   
                            <instanceId>JIRA</instanceId>  
                            <version>${jira.version}</version>
                            <!-- <dataVersion>${jira.version}</dataVersion> -->
                            <dataPath>${basedir}/src/main/resources/jira-home</dataPath>
                            <dataSources>          
                                <dataSource>            
                                    <jndi>jdbc/JiraDS</jndi>
                                    <url>jdbc:mysql://localhost:3306/jiradb</url>
                                    <driver>com.mysql.jdbc.Driver</driver>
                                    <username>root</username> 
                                    <password>123456</password>  
                                    <libArtifacts>                   
                                        <libArtifact>                 
                                            <groupId>mysql</groupId>        
                                            <artifactId>mysql-connector-java</artifactId>    
                                            <version>5.1.22</version>           
                                        </libArtifact>
                                    </libArtifacts>
                                </dataSource>
                            </dataSources>
                        </product>
                    </products>
                </configuration>
            </plugin>
 
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.5.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
 
            <plugin>
                <groupId>com.atlassian.plugin</groupId>
                <artifactId>atlassian-spring-scanner-maven-plugin</artifactId>
                <version>${atlassian.spring.scanner.version}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>atlassian-spring-scanner</goal>
                        </goals>
                        <phase>process-classes</phase>
                    </execution>
                </executions>
                <configuration>
                    <scannedDependencies>
                        <dependency>
                            <groupId>com.atlassian.plugin</groupId>
                            <artifactId>atlassian-spring-scanner-external-jar</artifactId>
                        </dependency>
                    </scannedDependencies>
                    <verbose>false</verbose>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

## 4. 运行 atlas-debug 启动 JIRA 时，若遇到如下错误，不要 clean，再次运行 atlas-debug 即可。
![运行时错误](https://s1.ax1x.com/2020/07/08/UVg46f.jpg)
## 5. 初次启动 JIRA 时，按照页面提示，填入 JIRA Software 试用许可，并设置 JIRA 管理员的用户信息。
## 6. 可以进行插件开发啦！
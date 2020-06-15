package com.chenq.jira.plugin.module.database;

import com.atlassian.jira.ofbiz.DefaultOfBizConnectionFactory;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * sql执行器
 * 2020/6/15 11:10
 * Created by chenq
 */
public class SqlExecutor {

    public static DbQueryResult executeSql(String sql) {

        DbQueryResult result = new DbQueryResult();
        if (StringUtils.isNotEmpty(sql)) {
            Connection connection ;
            try {
                connection = new DefaultOfBizConnectionFactory().getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                result.setError(e.getMessage());
                return result;
            }

            try {
                result.setSql(sql);
                Statement stmt = connection.createStatement();
                if (!stmt.execute(sql)) {
                    // 无查询结果（如：非查询语句）
                    result.setResultType(0);
                    result.setUpdatedCount(stmt.getUpdateCount());
                } else {
                    // 有查询结果
                    result.setResultType(1);
                    ResultSet resultSet = stmt.getResultSet();
                    ResultSetMetaData rsm = resultSet.getMetaData();

                    int columnCount = rsm.getColumnCount();

                    for (int nCol = 1; nCol <= columnCount; ++nCol) {
                        result.addColumn(rsm.getColumnName(nCol));
                    }

                    int rowsCount = 0;

                    while (true) {
                        if (!resultSet.next()) {
                            resultSet.close();
                            break;
                        }
                        result.setRowsCount(++rowsCount);
                        // LinkedHashMap 可保证顺序与 rows 一致
                        Map<String, String> row = new LinkedHashMap<>();
                        for (int nCol = 1; nCol <= columnCount; ++nCol) {
                            // 根据列数，从左到右建立起一整行
                            row.put(rsm.getColumnName(nCol), resultSet.getObject(nCol) != null ? String.valueOf(resultSet.getObject(nCol)) : "");
                        }
                        // 新增一行
                        result.addRow(row);
                    }
                }

                return result;
            } catch (Exception e) {
                result.setError(e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    result.setError(e.getMessage());
                }
            }
        } else {
            result.setError("Bad request, probably sql parameter missing.");
        }

        return result;
    }

}
package com.chenq.jira.plugin.module.database;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class DbQueryResult {

    @XmlElement(name = "resultType")
    private int resultType;

    @XmlElement(name = "updatedCount")
    private int updatedCount;

    @XmlElement(name = "error")
    private String error;

    @XmlElement(name = "sql")
    private String sql;

    @XmlElement(name = "columns")
    private List<String> columns = new ArrayList<>();

    @XmlElement(name = "rowsCount")
    private int rowsCount;

    @XmlElement(name = "rows")
    private List<Map<String, String>> rows = new ArrayList<>();


    public void addColumn(String column) {
        this.columns.add(column);
    }

    public void addRow(Map<String, String> row) {
        this.rows.add(row);
    }

}

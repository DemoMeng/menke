package com.framework.core.modules.gen.po;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framework.core.modules.gen.entity.GenTable;

import java.util.List;

/**
 * @author :Sun.Han
 * @since :2017年04月16日
 */

public class GenTableInfo {

    private TableInfo tableInfo;

    @JsonIgnoreProperties(value = "propertyType")
    private GenTable genTable;

    List<GenTableFieldInfo> genTableFieldList;

    public List<GenTableFieldInfo> getGenTableFieldList() {
        return genTableFieldList;
    }

    public void setGenTableFieldList(List<GenTableFieldInfo> genTableFieldList) {
        this.genTableFieldList = genTableFieldList;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public GenTable getGenTable() {
        return genTable;
    }

    public void setGenTable(GenTable genTable) {
        this.genTable = genTable;
    }
}

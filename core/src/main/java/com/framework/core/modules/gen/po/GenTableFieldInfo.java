package com.framework.core.modules.gen.po;

import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.framework.core.modules.gen.entity.GenTableField;

/**
 * @author :Sun.Han
 * @since :2017年04月16日
 */
public class GenTableFieldInfo {

    private TableField tableField;

    private GenTableField genTableField;


    public GenTableField getGenTableField() {
        return genTableField;
    }

    public void setGenTableField(GenTableField genTableField) {
        this.genTableField = genTableField;
    }

    public TableField getTableField() {
        return tableField;
    }

    public void setTableField(TableField tableField) {
        this.tableField = tableField;
    }

}

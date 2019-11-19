package com.framework.core.modules.gen.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.framework.core.GenericPo;

import static com.baomidou.mybatisplus.toolkit.StringUtils.underlineToCamel;

/**
 * <p>
 * 业务表字段
 * </p>
 *
 * @author Sun.Han
 * @since 2017-04-16
 */
@TableName("gen_table_field")
public class GenTableField extends GenericPo<Integer, GenTableField> {
    /**
     * 对应表名字
     */
    @TableField(value = "table_name", validate = FieldStrategy.NOT_EMPTY)
    private String tableName;
    /**
     * 字段名称
     */
    @TableField(validate = FieldStrategy.NOT_EMPTY)
    private String name;

    @TableField(validate = FieldStrategy.NOT_EMPTY)
    private String label;
    /**
     * 可空
     */
    @TableField("nullable")
    private Boolean nullable;

    /**
     * 是否编辑字段
     */
    @TableField("savable")
    private Boolean savable;
    /**
     * 是否列表字段
     */
    @TableField("listable")
    private Boolean listable;
    /**
     * 是否查询字段
     */
    @TableField("queryable")
    private Boolean queryable;

    @TableField("query_type")
    private String queryType;

    /**
     * 显示类型
     */
    @TableField("show_type")
    private String showType;
    /**
     * 字典类型
     */
    @TableField("dict_type")
    private String dictType;


    private Integer sort;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public Boolean getSavable() {
        return savable;
    }

    public void setSavable(Boolean savable) {
        this.savable = savable;
    }

    public Boolean getListable() {
        return listable;
    }

    public void setListable(Boolean listable) {
        this.listable = listable;
    }

    public Boolean getQueryable() {
        return queryable;
    }

    public void setQueryable(Boolean queryable) {
        this.queryable = queryable;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPropertyName(){
        return underlineToCamel(this.name);
    }
}

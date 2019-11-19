package com.framework.core.modules.gen.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.framework.core.GenericPo;
import com.framework.core.utils.SystemPath;

import java.util.List;

/**
 * <p>
 * 业务表
 * </p>
 *
 * @author Sun.Han
 * @since 2017-04-16
 */
@TableName("gen_table")
public class GenTable extends GenericPo<Integer, GenTable> {

    /**
     * tableName
     */
    @TableField(validate = FieldStrategy.NOT_EMPTY)
    private String name;

    private String prefix;

    /**
     * 生成文件的输出目录
     */

    @TableField("output_dir")
    private String outputDir = SystemPath.getSysPath() + "core\\src\\main\\java";
    /**
     * 开发人员
     */
    private String author;
    /**
     * 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
     */
    @TableField("parent_module_name")
    private String parentModuleName = "com.framework.core.modules";
    /**
     * 父包模块名。
     */
    @TableField("module_name")
    private String moduleName;
    /**
     * 是否覆盖已有文件
     */
    @TableField("file_override")
    private boolean fileOverride = false;

    @TableField("menu_name")
    private String menuName;

    @TableField("menu_icon")
    private String menuIcon;


    @TableField(exist = false)
    private List<GenTableField> genTableFieldList;

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getParentModuleName() {
        return parentModuleName;
    }

    public void setParentModuleName(String parentModuleName) {
        this.parentModuleName = parentModuleName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public boolean isFileOverride() {
        return fileOverride;
    }

    public void setFileOverride(boolean fileOverride) {
        this.fileOverride = fileOverride;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GenTableField> getGenTableFieldList() {
        return genTableFieldList;
    }

    public void setGenTableFieldList(List<GenTableField> genTableFieldList) {
        this.genTableFieldList = genTableFieldList;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }
}

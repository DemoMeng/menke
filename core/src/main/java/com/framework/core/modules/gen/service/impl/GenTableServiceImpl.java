package com.framework.core.modules.gen.service.impl;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.framework.core.modules.gen.entity.GenTable;
import com.framework.core.modules.gen.entity.GenTableField;
import com.framework.core.modules.gen.mapper.GenTableFieldMapper;
import com.framework.core.modules.gen.mapper.GenTableMapper;
import com.framework.core.modules.gen.po.GenTableFieldInfo;
import com.framework.core.modules.gen.po.GenTableInfo;
import com.framework.core.modules.gen.service.GenTableFieldService;
import com.framework.core.modules.gen.service.GenTableService;
import com.framework.core.modules.sys.entity.Menu;
import com.framework.core.modules.sys.mapper.MenuMapper;
import com.framework.core.utils.BeanUtils;
import com.framework.core.utils.StringUtils;
import com.framework.core.utils.SystemPath;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.framework.core.utils.StringUtils.underScoreCase2CamelCase;

/**
 * <p>
 * 业务表 服务实现类
 * </p>
 *
 * @author Sun.Han
 * @since 2017-04-16
 */

@Service
public class GenTableServiceImpl extends ServiceImpl<GenTableMapper, GenTable> implements GenTableService {

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;
    @Value("${spring.datasource.username}")
    private String datasourceUsername;
    @Value("${spring.datasource.password}")
    private String datasourcePassword;
    @Value("${spring.datasource.type}")
    private String datasourceType;
    @Value("${spring.datasource.driver-class-name}")
    private String datasourceDriverClassName;



    @Autowired
    private GenTableFieldService genTableFieldService;

    @Autowired
    private GenTableFieldMapper genTableFieldMapper;
    @Autowired
    private GenTableService genTableService;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<GenTableInfo> findTableList(String name) {
        return baseMapper.findTableList(name);
    }


    public List<TableInfo> tableList() {
        DataSourceConfig dsc = getDataSourceConfig();
        ConfigBuilder configBuilder = new ConfigBuilder(
                null,dsc,null,null,null);
        return configBuilder.getTableInfoList();
    }

    public boolean insert(GenTable entity) {
        return super.insert(entity);
    }

    public void save(GenTable genTable) {
        baseMapper.updateById(genTable);

        List<GenTableField> list = genTable.getGenTableFieldList();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (GenTableField field : list) {
            if (field.getSavable() == null) {
                field.setSavable(false);
            }
            if (field.getListable() == null) {
                field.setListable(false);
            }
            if (field.getQueryable() == null) {
                field.setQueryable(false);
            }
            genTableFieldMapper.updateById(field);
        }
    }


    public GenTable selectOneByName(String name) {
        List<GenTable> list = selectByMap(ImmutableMap.of("name", name));
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public Map<String, Object> info() {

        Map<String, Object> result = new HashMap<>();
        List<GenTableInfo> list = new ArrayList<>();
        Map<String, GenTableInfo> tableInfoMap = new HashMap<>();

        List<TableInfo> tableInfoList = genTableService.tableList();

        for (TableInfo tableInfo : tableInfoList) {
            GenTableInfo genTableInfo = new GenTableInfo();
            genTableInfo.setTableInfo(tableInfo);

            GenTable table = genTableService.selectOneByName(tableInfo.getName());

            if (table != null) {
                genTableInfo.setGenTable(table);
            } else {
                table = new GenTable();
                table.setName(tableInfo.getName());
                insert(table);
                genTableInfo.setGenTable(table);
            }
            list.add(genTableInfo);

            List<GenTableFieldInfo> genTableFieldInfoList = new ArrayList<>();
            for (TableField tableField : tableInfo.getFields()) {
                GenTableFieldInfo genTableFieldInfo = new GenTableFieldInfo();
                genTableFieldInfo.setTableField(tableField);
                GenTableField field = genTableFieldService.selectOneByName(tableField.getName(), tableInfo.getName());
                if (field != null) {
                    genTableFieldInfo.setGenTableField(field);
                } else {
                    field = new GenTableField();
                    field.setName(tableField.getName());
                    field.setLabel(tableField.getComment());
                    field.setTableName(tableInfo.getName());
                    genTableFieldService.insert(field);
                    genTableFieldInfo.setGenTableField(field);
                }

                genTableFieldInfoList.add(genTableFieldInfo);
            }
            genTableInfo.setGenTableFieldList(genTableFieldInfoList);
            tableInfoMap.put(tableInfo.getName(), genTableInfo);

        }
        result.put("tableInfoMap", tableInfoMap);
        result.put("list", list);
        return result;
    }


    public void insertMenu(GenTable genTable) {
        String path = "admin/" + genTable.getModuleName().replace(".", "/") + "/" +underScoreCase2CamelCase(genTable.getName().replace(genTable.getPrefix(),""))+ "/list.html";

        List<Menu> menus = menuMapper.selectByMap(ImmutableMap.of("href", path));
        if (menus.size() > 0) {
            return;
        }
        Menu menu = new Menu();
        menu.setIcon(StringUtils.isEmpty(genTable.getMenuIcon()) ? "icon-folder" : genTable.getMenuIcon());
        menu.setHref(path);
        menu.setName(StringUtils.isEmpty(genTable.getMenuName()) ? "为命名" : genTable.getMenuName());
        menu.setPermission(genTable.getModuleName() + ":" + NamingStrategy.removePrefixAndCamel(genTable.getName(), new String[]{genTable.getPrefix()}));
        menu.setCreateDate(new Date());
        menu.setUpdateDate(new Date());
        menu.setParentId(0);
        menu.setParentIds(",0,");
        Integer maxSort = menuMapper.getMaxSort(null);
        menu.setSort(maxSort + 10);
        menu.setDelFlag("0");

        menuMapper.insert(menu);

        Menu viewPermission = new Menu();
        viewPermission.setDelFlag("0");
        viewPermission.setSort(10);
        viewPermission.setHref("");
        viewPermission.setName("查看");
        viewPermission.setPermission("view");
        viewPermission.setCreateDate(new Date());
        viewPermission.setUpdateDate(new Date());
        viewPermission.setParentIds(",0," + menu.getId() + ",");
        viewPermission.setParentId(menu.getId());
        viewPermission.setShow(false);
        menuMapper.insert(viewPermission);


        Menu editPermission = new Menu();
        editPermission.setDelFlag("0");
        editPermission.setSort(20);
        editPermission.setHref("");
        editPermission.setName("编辑");
        editPermission.setPermission("edit");
        editPermission.setCreateDate(new Date());
        editPermission.setUpdateDate(new Date());
        editPermission.setParentIds(",0," + menu.getId() + ",");
        editPermission.setParentId(menu.getId());
        editPermission.setShow(false);
        menuMapper.insert(editPermission);
    }





    public AutoGenerator initGenerator(GenTable genTable) {
        AutoGenerator mpg = new AutoGenerator();

        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(genTable.getOutputDir());
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(false);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setOpen(false);
        gc.setAuthor(genTable.getAuthor());
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = getDataSourceConfig();
        mpg.setDataSource(dsc);

        mpg.setStrategy(strategyConfig(genTable.getName(), genTable.getPrefix()));
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(genTable.getParentModuleName());
        pc.setModuleName(genTable.getModuleName());
        mpg.setPackageInfo(pc);


        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                genTable.setProperty("saveTitle", "${param.id???string('编辑','新建')}");
                genTable.setProperty("saveId", "${param.id!''}");
                genTable.setProperty("include", "<#include ");
                genTable.setProperty("controllerPackage", "com.framework.admin.controller." + genTable.getModuleName());
                Map<String, Object> map = BeanUtils.beanToMap(genTable);
                this.setMap(map);
            }
        };
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        // 生成list.ftl页面
        focList.add(new FileOutConfig("/template/list.ftl.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return SystemPath.getSysPath() + "admin\\src\\main\\resources\\templates\\admin\\" + genTable.getModuleName().replace(".", "\\") + "\\" + tableInfo.getEntityPath() + "\\list.ftl";
            }
        });
        // 生成 save.ftl页面
        focList.add(new FileOutConfig("/template/save.ftl.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return SystemPath.getSysPath() + "admin\\src\\main\\resources\\templates\\admin\\" + genTable.getModuleName().replace(".", "\\") + "\\" + tableInfo.getEntityPath() + "\\save.ftl";
            }
        });

        // 生成Controller页面
        focList.add(new FileOutConfig("/template/controller.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {

                return SystemPath.getSysPath() + "admin\\src\\main\\java\\com\\framework\\admin\\controller\\" + genTable.getModuleName().replace(".", "\\") + "\\" + tableInfo.getEntityName() + "Controller.java";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 关闭默认Controller生成
        TemplateConfig tc = new TemplateConfig();
        tc.setController(null);
        mpg.setTemplate(tc);
        return mpg;
    }

    private DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName(datasourceDriverClassName);
        dsc.setUsername(datasourceUsername);
        dsc.setPassword(datasourcePassword);
        dsc.setUrl(dataSourceUrl);
        return dsc;
    }

    private static StrategyConfig strategyConfig(String tableName, String tablePrefix) {
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 字段名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        if (!StringUtils.isEmpty(tablePrefix)) {
            strategy.setTablePrefix(new String[]{tablePrefix});// 此处可以修改为您的表前缀
        }

        if (!StringUtils.isEmpty(tableName)) {
            strategy.setInclude(new String[]{tableName}); // 需要生成的表
        }
        return strategy;
    }

}

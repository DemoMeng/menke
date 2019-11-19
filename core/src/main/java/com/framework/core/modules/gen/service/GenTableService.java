package com.framework.core.modules.gen.service;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.service.IService;
import com.framework.core.modules.gen.entity.GenTable;
import com.framework.core.modules.gen.po.GenTableInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 业务表 服务类
 * </p>
 *
 * @author Sun.Han
 * @since 2017-04-16
 */

public interface GenTableService extends IService<GenTable> {
    List<GenTableInfo> findTableList(String name);

    List<TableInfo> tableList();

    Map<String, Object> info();

    GenTable selectOneByName(String name);

    void save(GenTable genTable);

    void insertMenu(GenTable genTable);

    AutoGenerator initGenerator(GenTable genTable);
}

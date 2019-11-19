package com.framework.core.modules.gen.service;

import com.baomidou.mybatisplus.service.IService;
import com.framework.core.modules.gen.entity.GenTableField;
import com.framework.core.modules.gen.po.GenTableFieldInfo;

import java.util.List;

/**
 * <p>
 * 业务表字段 服务类
 * </p>
 *
 * @author Sun.Han
 * @since 2017-04-16
 */
public interface GenTableFieldService extends IService<GenTableField> {
    List<GenTableFieldInfo> findTableFieldList(String name);

    GenTableField selectOneByName(String name,String tableName);
}

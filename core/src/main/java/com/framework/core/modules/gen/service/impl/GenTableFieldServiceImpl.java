package com.framework.core.modules.gen.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.framework.core.modules.gen.entity.GenTableField;
import com.framework.core.modules.gen.mapper.GenTableFieldMapper;
import com.framework.core.modules.gen.po.GenTableFieldInfo;
import com.framework.core.modules.gen.service.GenTableFieldService;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务表字段 服务实现类
 * </p>
 *
 * @author Sun.Han
 * @since 2017-04-16
 */
@Service
public class GenTableFieldServiceImpl extends ServiceImpl<GenTableFieldMapper, GenTableField> implements GenTableFieldService {


    @Override
    public List<GenTableFieldInfo> findTableFieldList(String name) {
        return baseMapper.findTableFieldList(name);
    }


    public boolean insert(GenTableField entity) {
        return super.insert(entity);
    }

    public GenTableField selectOneByName(String name,String tableName) {
        List<GenTableField> list = selectByMap(ImmutableMap.of("name", name,"table_name",tableName));
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}

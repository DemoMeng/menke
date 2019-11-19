package com.framework.core.modules.gen.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.framework.core.modules.gen.entity.GenTableField;
import com.framework.core.modules.gen.po.GenTableFieldInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 业务表字段 Mapper 接口
 * </p>
 *
 * @author Sun.Han
 * @since 2017-04-16
 */
public interface GenTableFieldMapper extends BaseMapper<GenTableField> {
    List<GenTableFieldInfo> findTableFieldList(@Param("name") String name);
}
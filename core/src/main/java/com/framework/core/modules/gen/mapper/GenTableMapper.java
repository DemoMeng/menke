package com.framework.core.modules.gen.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.framework.core.modules.gen.entity.GenTable;
import com.framework.core.modules.gen.po.GenTableInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 业务表 Mapper 接口
 * </p>
 *
 * @author Sun.Han
 * @since 2017-04-16
 */
public interface GenTableMapper extends BaseMapper<GenTable> {

    List<GenTableInfo> findTableList(@Param("name") String name);

}
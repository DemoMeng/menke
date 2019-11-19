package com.framework.admin.controller.gen;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.framework.admin.controller.GenericController;
import com.framework.core.message.ResponseMessage;
import com.framework.core.modules.gen.entity.GenTable;
import com.framework.core.modules.gen.service.GenTableFieldService;
import com.framework.core.modules.gen.service.GenTableService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author SunHan
 * @since 2017-01-21
 */
@RestController
@RequestMapping("gen/table")
@PreAuthorize("hasAuthority('sys:gen')")
public class GenTableController extends GenericController<GenTable, Integer> {

    @Autowired
    private GenTableService genTableService;

    @Autowired
    private GenTableFieldService genTableFieldService;
    @Override
    protected GenTableService getService() {
        return genTableService;
    }

    /**
     * 查询列表,并返回查询结果
     */
    @GetMapping(value = "info")
    @PreAuthorize("hasAuthority('sys:gen')")
    public Map<String, Object> list() {
        return getService().info();
    }

    @PutMapping("save")
    @PreAuthorize("hasAuthority('sys:gen')")
    public ResponseMessage save(GenTable po) {
        getService().save(po);

        return ResponseMessage.ok();
    }

    @PostMapping("generator")
    @PreAuthorize("hasAuthority('sys:gen')")
    public ResponseMessage generator(String genTableId) {
        GenTable genTable = getService().selectById(genTableId);
        genTable.setGenTableFieldList(genTableFieldService.selectByMap(ImmutableMap.of("table_name", genTable.getName())));
        AutoGenerator mpg = genTableService.initGenerator(genTable);
        mpg.execute();

        // 保存菜单信息
        getService().insertMenu(genTable);
        return ResponseMessage.ok();
    }


}

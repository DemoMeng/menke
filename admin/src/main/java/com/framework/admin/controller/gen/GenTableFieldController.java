package com.framework.admin.controller.gen;

import com.framework.admin.controller.GenericController;
import com.framework.core.modules.gen.entity.GenTableField;
import com.framework.core.modules.gen.service.GenTableFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author SunHan
 * @since 2017-01-21
 */
@RestController
@RequestMapping("/sys/genTableField")
@PreAuthorize("hasAuthority('sys:gen')")
public class GenTableFieldController extends GenericController<GenTableField,Integer> {

    @Autowired
    private GenTableFieldService genTableFieldService;

    @Override
    protected GenTableFieldService getService() {
        return genTableFieldService;
    }

}

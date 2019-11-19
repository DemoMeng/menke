package com.framework.admin.controller.sys;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.framework.admin.controller.GenericController;
import com.framework.core.exception.BusinessException;
import com.framework.core.exception.NotFoundException;
import com.framework.core.message.ResponseMessage;
import com.framework.core.modules.sys.entity.Role;
import com.framework.core.modules.sys.service.RoleMenuService;
import com.framework.core.modules.sys.service.RoleService;
import com.framework.core.po.DataTablesPo;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import static com.framework.core.message.ResponseMessage.created;
import static com.framework.core.message.ResponseMessage.ok;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author SunHan
 * @since 2017-01-21
 */
@RestController
@RequestMapping("/sys/role")
@PreAuthorize("hasAuthority('sys:role')")
public class RoleController extends GenericController<Role,Integer> {

    @Resource
    private RoleService roleService;


    @Resource
    private RoleMenuService roleMenuService;

    public RoleService getService() {
        return roleService;
    }


    @PostMapping("roleMenu/{id}")
    public ResponseMessage updateRoleMenu(@PathVariable Integer id, String menuIds) {
        roleMenuService.saveRoleMenu(id, menuIds);
        return ResponseMessage.ok();
    }

    @GetMapping("roleMenu/{id}")
    public ResponseMessage getMenuByRoleId(@PathVariable Integer id) {
        return ResponseMessage.ok(roleMenuService.getMenuIdsByRoleId(id));
    }

}

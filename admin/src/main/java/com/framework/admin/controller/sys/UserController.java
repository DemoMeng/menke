package com.framework.admin.controller.sys;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.framework.admin.controller.GenericController;
import com.framework.core.exception.BusinessException;
import com.framework.core.exception.NotFoundException;
import com.framework.core.message.ResponseMessage;
import com.framework.core.modules.sys.entity.User;
import com.framework.core.modules.sys.service.UserRoleService;
import com.framework.core.modules.sys.service.UserService;
import com.framework.core.po.DataTablesPo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.framework.core.message.ResponseMessage.created;
import static com.framework.core.message.ResponseMessage.ok;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author SunHan
 * @since 2017-01-21
 */
@RestController
@RequestMapping("/sys/user")
@PreAuthorize("hasAuthority('sys:user')")
public class UserController extends GenericController<User,Integer> {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    protected UserService getService() {
        return userService;
    }


    @GetMapping(value = "/{id}")
    public ResponseMessage info(@PathVariable("id") Integer id) {
        User po = getService().selectById(id);
        if (po == null)
            throw new NotFoundException("data is not found!");
        List<String> roleIds = userRoleService.getRoleIdsByUserId(id);
        po.setRoleIds(String.join(",", roleIds));
        return ok(po);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage add(User po) {
        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        po.setPassword(passwordEncoder.encodePassword(po.getPassword(),""));
        getService().insert(po);
        getService().editRole(po);
        return created(po);
    }

    @PutMapping(value = "/{id}")
    public ResponseMessage update(@PathVariable("id") Integer id, User object) {
        User old = getService().selectById(id);
        assertFound(old, "data is not found!");
        if (!StringUtils.isEmpty(object.getPassword())){
            Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
            object.setPassword(passwordEncoder.encodePassword(object.getPassword(),""));
        }else {
            // 否则不更新密码字段
            object.setPassword(null);
        }
        Boolean b = getService().updateById(object);
        getService().editRole(object);
        return ok(b);
    }

}

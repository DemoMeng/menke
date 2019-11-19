package com.framework.admin.controller.sys;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.framework.admin.controller.GenericController;
import com.framework.core.exception.BusinessException;
import com.framework.core.exception.NotFoundException;
import com.framework.core.message.ResponseMessage;
import com.framework.core.modules.sys.entity.ErrorMessage;
import com.framework.core.modules.sys.service.ErrorMessageService;
import com.framework.core.po.DataTablesPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

import static com.framework.core.message.ResponseMessage.created;
import static com.framework.core.message.ResponseMessage.ok;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author SunHan
 * @since 2017-01-21
 */
@RestController
@RequestMapping("/sys/errorMessage")
@PreAuthorize("hasAuthority('sys:error')")
public class ErrorMessageController extends GenericController<ErrorMessage,Integer> {
    @Autowired
    private ErrorMessageService errorMessageService;
    @Override
    protected ErrorMessageService getService() {
        return errorMessageService;
    }
}

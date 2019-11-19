package com.framework.admin.advice;

import com.framework.core.exception.*;
import com.framework.core.message.ResponseMessage;
import com.framework.core.modules.sys.entity.ErrorMessage;
import com.framework.core.modules.sys.service.ErrorMessageService;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @author Han.Sun
 */
@ControllerAdvice(annotations = {RestController.class, ResponseBody.class})
@Order(1)
public class RestControllerExceptionTranslator {


    @Resource
    private ErrorMessageService errorMessageService;

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseMessage handleException(ValidationException exception) {
        return ResponseMessage.error(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseMessage noHandlerFoundException(NoHandlerFoundException exception) {
        return ResponseMessage.error(exception.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    ResponseMessage handleException(BusinessException exception, HttpServletResponse response) {
        response.setStatus(exception.getStatus());
        return ResponseMessage.error(exception.getMessage(), exception.getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ResponseMessage handleException(NotFoundException exception) {
        return ResponseMessage.error(exception.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ResponseMessage handleException(BindException exception) {
        return ResponseMessage.error(exception.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    ResponseMessage handleException(AccessDeniedException exception) {
        return ResponseMessage.error(exception.getMessage(),  HttpStatus.UNAUTHORIZED.value());
    }
    @ExceptionHandler(CookieTheftException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    ResponseMessage handleException(CookieTheftException exception) {
        return ResponseMessage.error(exception.getMessage(), HttpStatus.UNAUTHORIZED.value());
    }


    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ResponseMessage handleException(Throwable exception) {
        ErrorMessage errorMessages = new ErrorMessage();
        errorMessages.setCreateDate(new Date());
        String fullClassName = exception.getStackTrace()[0].getClassName();
        errorMessages.setClassName(fullClassName.substring(fullClassName.lastIndexOf('.') +1,fullClassName.length()));
        errorMessages.setMethodName(exception.getStackTrace()[0].getMethodName());
        errorMessages.setLineNumber(exception.getStackTrace()[0].getLineNumber());
        ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();
        exception.printStackTrace(new java.io.PrintWriter(buf, true));
        String  stackTrace = buf.toString();
        try {
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        errorMessages.setStackTrace(stackTrace);
        errorMessages.setMessage(exception.getMessage());
        errorMessages.setStatus(0);
        errorMessageService.asyncInsert(errorMessages);
        exception.printStackTrace();
        return ResponseMessage.error("服务器异常，请联系管理员", 500);
    }

}
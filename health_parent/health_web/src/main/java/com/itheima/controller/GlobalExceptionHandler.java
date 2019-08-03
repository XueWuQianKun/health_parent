package com.itheima.controller;

import com.itheima.entity.Result;
import com.itheima.exception.HealthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    // 记录日志, logback.xml
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HealthException.class)
    @ResponseBody
    public Result handlerHealthException(HealthException e){
        log.error("验证失败",e);
        return new Result(false,e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result handlerRuntimeException(RuntimeException e){
        log.error("RuntimeException",e);
        //e.printStackTrace();// outputstream findBug
        return new Result(false,e.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException",e);
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuffer sb = new StringBuffer();
        for (FieldError fieldError : fieldErrors) {
            sb.append(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            sb.append(", ");
        }
        if(sb.length() > 0){
            sb.setLength(sb.length()-2);
        }
        String message = sb.toString();
        //e.printStackTrace();// outputstream findBug
        return new Result(false,message);
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handlerException(Exception e){
        log.error("出错了",e);
        return new Result(false,"出错了，请联系管理员");
    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result handlerAccessDeniedException(AccessDeniedException e){
        log.error("权限不足",e);
        return new Result(false,"权限不足");
    }
}

package com.chinare.axe.handler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chinare.axe.Result;
import com.chinare.axe.auth.AuthException;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    Log log = Logs.get();

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result defaultErrorHandler(HttpServletResponse response, Exception e) {
        log.error(e);
        return Result.exception(e);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handle(ValidationException e) {
        log.error(e);
        if (e instanceof ConstraintViolationException) {

            final List<NutMap> errors = new ArrayList<>();
            final List<String> infos = Lang.list();

            ((ConstraintViolationException) e).getConstraintViolations().stream().forEach(error -> {
                infos.add(error.getMessage());
                errors.add(NutMap.NEW()
                                 .addv("msg", error.getMessage())
                                 .addv("obj", error.getConstraintDescriptor())
                                 .addv("arguments", error.getExecutableParameters()));
            });
            return Result.exception(infos).addExtData("details", errors);
        }
        return Result.fail("参数不正确");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result validate(HttpServletResponse response, MethodArgumentNotValidException e) {
        log.error(e);
        final List<NutMap> errors = new ArrayList<>();
        final List<String> infos = Lang.list();
        e.getBindingResult().getAllErrors().stream().forEach(error -> {
            infos.add(error.getDefaultMessage());
            errors.add(NutMap.NEW()
                             .addv("msg", error.getDefaultMessage())
                             .addv("obj", error.getObjectName())
                             .addv("arguments", error.getArguments())
                             .addv("code",
                                   error.getCode())
                             .addv("codes", error.getCodes()));
        });
        return Result.exception(infos).addExtData("details", errors);
    }

    @ExceptionHandler(value = AuthException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result auth(HttpServletResponse response, Exception e) {
        log.error(e);
        return Result.exception("没有权限进行操作!");
    }
}

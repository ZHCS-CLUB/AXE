package com.chinare.rop.server;

import javax.servlet.http.HttpServletResponse;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chinare.rop.core.ROPResponse;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@RestController
@ControllerAdvice(annotations = ROP.class)
public class ROPExceptionHandler {

    Log log = Logs.get();

    @ExceptionHandler(value = ROPException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ROPResponse rop(HttpServletResponse response, ROPException e) {
        log.error("error=>", e);
        return ROPResponse.exception(e);
    }
}

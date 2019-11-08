package com.china.rop.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinare.axe.Result;
import com.chinare.axe.apm.APM;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@APM
@RestController
@RequestMapping("/apm")
public class APMController {

    @GetMapping("test")
    public Result test() {
        return Result.success();
    }
}

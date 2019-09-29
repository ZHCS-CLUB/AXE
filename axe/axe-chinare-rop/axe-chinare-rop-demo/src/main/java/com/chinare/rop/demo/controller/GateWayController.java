package com.chinare.rop.demo.controller;

import org.nutz.lang.util.NutMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@RestController
@RequestMapping("gate")
public class GateWayController {

    public NutMap test() {
        return NutMap.NEW().addv("user", "kerbores");
    }
}

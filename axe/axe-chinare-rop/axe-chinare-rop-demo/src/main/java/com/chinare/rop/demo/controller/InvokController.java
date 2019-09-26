package com.chinare.rop.demo.controller;

import org.nutz.lang.util.NutMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinare.rop.demo.service.RopService;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@RestController
public class InvokController {

    @Autowired
    RopService ropService;

    @GetMapping("invoke")
    public NutMap invoke() {
        // 实际项目中不可能自己通过网络调自己的服务,此处仅作为演示使用
        return ropService.test();
    }

    @GetMapping("test/rest")
    public NutMap name(@RequestParam("k") long k) {
        return NutMap.NEW().addv("k", k);
    }
}

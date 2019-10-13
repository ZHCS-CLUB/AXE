package com.china.rop.demo.controller;

import org.nutz.lang.util.NutMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.china.rop.demo.service.RopService;

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

    @GetMapping("invoke/file")
    public NutMap invokeFile() {
        // 实际项目中不可能自己通过网络调自己的服务,此处仅作为演示使用
        return ropService.file();
    }

    @GetMapping("invoke/get")
    public NutMap invokeGet() {
        // 实际项目中不可能自己通过网络调自己的服务,此处仅作为演示使用
        return ropService.get();
    }

    @GetMapping("invoke/post")
    public NutMap invokePost() {
        // 实际项目中不可能自己通过网络调自己的服务,此处仅作为演示使用
        return ropService.post();
    }

    @GetMapping("test/rest")
    public NutMap name(@RequestParam("k") long k) {
        return NutMap.NEW().addv("k", k);
    }

    @PostMapping("post/test")
    public NutMap post(@RequestParam long id) {
        return NutMap.NEW().addv("id", id);
    }

    @GetMapping("wms")
    public NutMap wms() {
        return ropService.testWms();
    }

}

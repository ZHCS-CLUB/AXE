package com.chinare.rop.demo.controller;

import org.nutz.lang.random.R;
import org.nutz.lang.util.NutMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinare.rop.server.ROP;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@RestController
@ROP
public class IndexController {

    @GetMapping("test")
    public NutMap get() {
        return NutMap.NEW().addv("t", R.UU16());
    }

    @PostMapping("test")
    public NutMap test(@RequestBody NutMap data) {
        return data;
    }
}

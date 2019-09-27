package com.chinare.rop.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.nutz.lang.random.R;
import org.nutz.lang.util.NutMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.chinare.rop.server.ROP;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@RestController
@ROP
public class IndexController {

    @PostMapping("file")
    public NutMap args(MultipartFile file, @RequestParam int id, HttpServletRequest request) {
        System.err.println(request.getClass().getName());
        file = ((StandardMultipartHttpServletRequest) request).getFile("file");
        return NutMap.NEW().addv("name", file.getOriginalFilename()).addv("id", id);
    }

    @GetMapping("test")
    public NutMap get() {
        return NutMap.NEW().addv("t", R.UU16());
    }

    @GetMapping("get")
    public NutMap get(@RequestParam long id, @RequestParam String name) {
        return NutMap.NEW().addv("id", id).addv("name", name);
    }

    @PostMapping("post")
    public NutMap post(@RequestParam long id, @RequestParam String name) {
        return NutMap.NEW().addv("id", id).addv("name", name);
    }

    @PostMapping("test")
    public NutMap test(@RequestBody NutMap data) {
        return data;
    }
}

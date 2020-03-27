package com.lawer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class democlass {
    @RequestMapping("/index")
    public String index(){
        return "你好";
    }
}

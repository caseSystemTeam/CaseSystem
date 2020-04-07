package com.lawer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("case")
public class CaseController {

    //跳转至案件详情界面
    @RequestMapping("/tocase")
    public String toCasePlan(){
        return "/html/casePlan";
    }

}

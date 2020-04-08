package com.lawer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawer.pojo.CaseFile;
import com.lawer.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("case")
public class CaseController {
    private ObjectMapper jsonutil = new ObjectMapper();

    @Autowired
    CaseService caseService;
    //跳转至案件详情界面
    @RequestMapping("/tocase")
    public String toCasePlan(){
        return "/html/casePlan";
    }

    //查询当前案件下所有的文件
    @RequestMapping("/getFileAll")
    @ResponseBody
    public String getFileAll(@RequestParam("caseId")String caseId){
        List<CaseFile> list = caseService.getFileAll(caseId);
        String json = null;
        try{
            json = jsonutil.writeValueAsString(list);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("json转换出了问题");
        }
        //将返回数据转成json格式
        return json;
    }

}

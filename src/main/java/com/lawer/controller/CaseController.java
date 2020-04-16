package com.lawer.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawer.pojo.CaseFile;
import com.alibaba.fastjson.JSON;
import com.lawer.common.ResultGson;
import com.lawer.pojo.User;
import com.lawer.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import java.util.Map;


@Controller
@RequestMapping("case")
public class CaseController {

    private ObjectMapper jsonutil = new ObjectMapper();

    @Autowired
    private CaseService caseService;

    //跳转至案件详情界面
    @RequestMapping("/tocase")
    public String toCasePlan(){
        return "/html/casePlan";
    }


    //查询当前案件下所有的文件
    @RequestMapping("/getFileAll")
    @ResponseBody
    public String getFileAll(@RequestParam("caseId")String caseId) {
        List<CaseFile> list = caseService.getFileAll(caseId);
        String json = null;
        try {
            json = jsonutil.writeValueAsString(list);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("json转换出了问题");
        }
        //将返回数据转成json格式
        return json;
    }

    @RequestMapping("addCase")
    @ResponseBody
    public ResultGson addCase(@RequestBody  String json, HttpSession session){
        Map<String, Object> mapJson = JSON.parseObject(json);
        User user =(User)session.getAttribute("us");
       try{
           caseService.addCase(mapJson,user);
       }catch (Exception e){
           e.printStackTrace();
           return ResultGson.error("执行出错");
       }
        return ResultGson.ok("执行成功");
    }

    @RequestMapping("deleteFileById")
    @ResponseBody
    public ResultGson deleteFileById(@RequestParam("fileid") String fileid,String filepath){
        filepath = filepath.replace("\"","\\");
        File file = new File(filepath);//根据指定的文件名创建File对象
        if (  file.exists() && file.isFile() ){ //要删除的文件存在且是文件
            if ( file.delete()){
                caseService.deleteFileById(fileid);
            }else{
                return ResultGson.error("删除文件失败");
            }
        }else{
            caseService.deleteFileById(fileid);
        }
        return ResultGson.ok("删除成功");
    }

    @RequestMapping("sendMessage")
    @ResponseBody
    public ResultGson sendMessage(@RequestParam("receiver") String receiver,
                                  @RequestParam("message")String message,
                                  @RequestParam("sender")String sender){

        //解析json数组
        List<User> receList = JSON.parseObject(receiver, new TypeReference<ArrayList<User>>(){});
        if(receList!=null||message!=null||sender!=null){
            try{
                caseService.addMessage(receList,message,sender);
            }catch (Exception e){
                e.printStackTrace();
                return ResultGson.error("发送失败");
            }

        }
        return ResultGson.ok("发送成功");
    }

    @RequestMapping("getCaseInfo")
    @ResponseBody
    public ResultGson getCaseInfo(@RequestParam("caseId") String caseId){
        Map<String,Object> map =null;
        try{
           map = caseService.getCaseInfo(caseId);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGson.error("获取案件信息出错");
        }

        return ResultGson.ok(map);
    }



}

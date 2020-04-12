package com.lawer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lawer.common.ResultGson;
import com.lawer.pojo.User;
import com.lawer.service.CaseListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("caseList")
public class CaseListController {
    @Autowired
    private CaseListService caseListService;

    //获取律所所有的案件
    @RequestMapping("getAllCase")
    @ResponseBody
    public String getAllCase(String page,String limit, String key, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        User user =(User)session.getAttribute("us");
        JSONObject result = new JSONObject();
        int pageSize = Integer.parseInt(limit);
        map.put("busId",user.getBusId());
        map.put("pageSize", pageSize);
        map.put("skipCount", (Integer.parseInt(page) - 1) * pageSize);
        if (key != null) {
            JSONObject json = JSONObject.parseObject(key);
            if (!("".equals(json.get("name"))) && !(null == json.get("name"))) {
                map.put("name", json.getString("name"));
            }
            if (!("".equals(json.get("lawerid"))) && !(null == json.get("lawerid"))) {
                map.put("lawerid", json.getString("lawerid"));
            }
            if (!("".equals(json.get("jstatus"))) && !(null == json.get("jstatus"))) {
                map.put("jstatus", json.getString("jstatus"));
            }
            if (!("".equals(json.get("rtime"))) && !(null == json.get("rtime"))) {
                String[] tricktime = json.get("rtime").toString().split(" - ");
                map.put("tricktime", tricktime[0]+ " 00:00:00");
                map.put("tricktime2", tricktime[1]+ " 23:59:59");
            }
        }
        List list = caseListService.getAllCase(map);
        int count = caseListService.getACaseCount(map);
        result.put("data",list);
        result.put("msg","请求成功");
        result.put("code", 0);
        result.put("count",count);
        return  JSON.toJSONString(result);


    }

    //获取个人的案件
    @RequestMapping("getCaseById")
    @ResponseBody
    public String getCaseById(String page,String limit, String key, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        User user =(User)session.getAttribute("us");
        JSONObject result = new JSONObject();
        int pageSize = Integer.parseInt(limit);
        map.put("pageSize", pageSize);
        map.put("skipCount", (Integer.parseInt(page) - 1) * pageSize);
        map.put("lawerid",user.getId());
        map.put("busId",user.getBusId());

        if (key != null) {
            JSONObject json = JSONObject.parseObject(key);

            map.put("jstatus", json.getString("jstatus"));

            if (!("".equals(json.get("name"))) && !(null == json.get("name"))) {
                map.put("name", json.getString("name"));
            }
            if (!("".equals(json.get("rtime"))) && !(null == json.get("rtime"))) {
                String[] tricktime = json.get("rtime").toString().split(" - ");
                map.put("tricktime", tricktime[0]+ " 00:00:00");
                map.put("tricktime2", tricktime[1]+ " 23:59:59");
            }



        }
        List list = caseListService.getCaseById(map);
        int count = caseListService.getCaseCount(map);
        result.put("data",list);
        result.put("msg","请求成功");
        result.put("code", 0);
        result.put("count",count);
        return  JSON.toJSONString(result);

    }


}

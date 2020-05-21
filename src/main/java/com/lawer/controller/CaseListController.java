package com.lawer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lawer.common.IpAdress;
import com.lawer.common.ResultGson;
import com.lawer.pojo.Log;
import com.lawer.pojo.User;
import com.lawer.service.CaseListService;
import com.lawer.service.LogService;
import com.lawer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("caseList")
public class CaseListController {
    @Autowired
    private CaseListService caseListService;
    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;

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
            if (!("".equals(json.get("pstatus"))) && !(null == json.get("pstatus"))) {
                map.put("pstatus", json.getString("pstatus"));
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
    //获取律所所有的案件
    @RequestMapping("getGroupCase")
    @ResponseBody
    public String getGroupCase(String page,String limit, String key, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        User user =(User)session.getAttribute("us");
        JSONObject result = new JSONObject();
        int pageSize = Integer.parseInt(limit);
        map.put("busId",user.getBusId());
        map.put("pageSize", pageSize);
        map.put("skipCount", (Integer.parseInt(page) - 1) * pageSize);
        map.put("groupuserid",user.getId());
        if (key != null) {
            JSONObject json = JSONObject.parseObject(key);
            if (!("".equals(json.get("name"))) && !(null == json.get("name"))) {
                map.put("name", json.getString("name"));
            }
            if (!("".equals(json.get("lawerid"))) && !(null == json.get("lawerid"))) {
                map.put("lawerid", json.getString("lawerid"));
            }
            if (!("".equals(json.get("pstatus"))) && !(null == json.get("pstatus"))) {
                map.put("pstatus", json.getString("pstatus"));
            }
            if (!("".equals(json.get("rtime"))) && !(null == json.get("rtime"))) {
                String[] tricktime = json.get("rtime").toString().split(" - ");
                map.put("tricktime", tricktime[0]+ " 00:00:00");
                map.put("tricktime2", tricktime[1]+ " 23:59:59");
            }
        }

        List list = caseListService.getGroupCase(map);
        int count = caseListService.getGroupCaseCount(map);
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
            if (!("".equals(json.get("pstatus"))) && !(null == json.get("pstatus"))) {
                map.put("pstatus", json.getString("pstatus"));
            }


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

    //案件转移
    @RequestMapping("transferPerson")
    @ResponseBody
    public ResultGson transferPerson(@RequestBody String json, HttpSession session, HttpServletRequest request){
        Map<String,Object> map =  JSON.parseObject(json);
        User user =(User)session.getAttribute("us");
        List<Map<String,Object>> list = (List<Map<String,Object>>)map.get("data");
        String lawerid =(String) map.get("lawerid");
        User us = userService.userById(lawerid);
        for(Map<String,Object> hmap:list){
            hmap.put("lawerid",lawerid);
            if(map.get("p_status")!=null){
                hmap.put("p_status", map.get("p_status"));
            }
            try{
                caseListService.transferPerson(hmap);
                Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),1,"转移案件","成功", "将案件\""+hmap.get("name")+"\"转移给\""+us.getName()+"\"成功",user.getBusId());
                logService.addLog(log);
            }catch (Exception e){
                Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),1,"转移案件","失败", "将案件\""+hmap.get("name")+"\"转移给\""+us.getName()+"\"失败",user.getBusId());
                logService.addLog(log);
            }

        }

        return ResultGson.ok("操作成功");


    }

    //删除案件
    @RequestMapping("deleteCase")
    @ResponseBody
    public ResultGson deleteCase(String id, HttpSession session, HttpServletRequest request){
        User user =(User)session.getAttribute("us");
        Map<String,Object> map = caseListService.SelectCaseById(id);

        try{
            caseListService.deleteCase(id);
        }catch (Exception e){
            Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),1,"删除案件","失败", "删除案件\""+map.get("name")+"\"",user.getBusId());
            logService.addLog(log);
            return ResultGson.error("删除失败");

        }
        Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),1,"删除案件","成功", "删除案件\""+map.get("name")+"\"",user.getBusId());
        logService.addLog(log);
        return ResultGson.ok("删除成功");
    }

    //查询案件
    @RequestMapping("selectCase")
    @ResponseBody
    public ResultGson selectCase(HttpSession session, HttpServletRequest request){
        User user =(User)session.getAttribute("us");
        String id =(String) session.getAttribute("caseId");
        Map<String,Object> map = caseListService.SelectCaseById(id);

        return ResultGson.ok(map);


    }

    //获取未分配案件
    @RequestMapping("getUnallocation")
    @ResponseBody
    public String getUnallocation(String page,String limit, String key, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        User user =(User)session.getAttribute("us");
        JSONObject result = new JSONObject();
        Map<String,Object> rmap=userService.selectRoleByUserId(user.getId());
        if(!rmap.get("role_id").equals("1")&&!rmap.get("role_id").equals("2")){
            map.put("userId",user.getId());
        }
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
            if (!("".equals(json.get("rtime"))) && !(null == json.get("rtime"))) {
                String[] tricktime = json.get("rtime").toString().split(" - ");
                map.put("tricktime", tricktime[0]+ " 00:00:00");
                map.put("tricktime2", tricktime[1]+ " 23:59:59");
            }
        }

        List list = caseListService.getUnallocation(map);
        int count = caseListService.getUnallocationCount(map);
        result.put("data",list);
        result.put("msg","请求成功");
        result.put("code", 0);
        result.put("count",count);
        return  JSON.toJSONString(result);
    }

    //修改案件
    @RequestMapping("updateCase")
    @ResponseBody
    public ResultGson updateCase(@RequestBody String json, HttpSession session, HttpServletRequest request) {
       Map<String,Object> map =JSON.parseObject(json);
        User user =(User)session.getAttribute("us");
        Map<String,Object> hmap = caseListService.SelectCaseById((String) map.get("Id"));
       try {
           caseListService.updateCase(map);
       }catch (Exception e){
           if(map.get("lawerid")!=null){
               User us = userService.userById((String) map.get("lawerid"));
               Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),1,"分配案件","失败", "将案件\""+hmap.get("name")+"\"分配给\""+us.getName()+"\"失败",user.getBusId());
               logService.addLog(log);
           }else{
               Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),1,"修改案件","失败", "修改案件\""+hmap.get("name")+"失败",user.getBusId());
               logService.addLog(log);
           }

           return ResultGson.error("分配失败");
       }
        if(map.get("lawerid")!=null){
            User us = userService.userById((String) map.get("lawerid"));
            Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),1,"分配案件","成功", "将案件\""+hmap.get("name")+"\"分配给\""+us.getName()+"\"成功",user.getBusId());
            logService.addLog(log);
        }else{
            Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),1,"修改案件","成功", "修改案件\""+map.get("name")+"成功",user.getBusId());
            logService.addLog(log);
        }
       return ResultGson.ok("分配成功");

    }

}

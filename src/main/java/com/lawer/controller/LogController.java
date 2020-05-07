package com.lawer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lawer.common.ResultGson;
import com.lawer.pojo.User;
import com.lawer.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志管理
 */
@Controller
@RequestMapping("log")
public class LogController {
    @Autowired
    private LogService logService;

    //获取日志信息
    @RequestMapping("getLog")
    @ResponseBody
    public String getLog(String page,String limit, String key,HttpSession session){
        Map<String,Object> map = new HashMap<>();
        JSONObject result = new JSONObject();
        User user=(User)session.getAttribute("us");
        int pageSize = Integer.parseInt(limit);
        map.put("busid",user.getBusId());
        map.put("pageSize", pageSize);
        map.put("skipCount", (Integer.parseInt(page) - 1) * pageSize);
        if (key != null) {
            JSONObject json = JSONObject.parseObject(key);

            map.put("style", json.getString("style"));

            if (!("".equals(json.get("username"))) && !(null == json.get("username"))) {
                map.put("username", json.getString("username"));
            }
            if (!("".equals(json.get("rtime"))) && !(null == json.get("rtime"))) {
                String[] tricktime = json.get("rtime").toString().split(" - ");
                map.put("tricktime", tricktime[0]+ " 00:00:00");
                map.put("tricktime2", tricktime[1]+ " 23:59:59");
            }
        }
        List list = logService.getAllLog(map);
        int count = logService.getLogCount(map);
        result.put("data",list);
        result.put("msg","请求成功");
        result.put("code", 0);
        result.put("count",count);
        return  JSON.toJSONString(result);

    }
    //获取日志信息
    @RequestMapping("deleteLog")
    @ResponseBody
    public ResultGson delteLog(String id){
        try{
            logService.deleteLog(id);
        }catch (Exception e){
            return ResultGson.error("删除失败");
        }

        return  ResultGson.ok("删除成功");

    }


}

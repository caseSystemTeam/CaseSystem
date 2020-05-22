package com.lawer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lawer.common.DateUtil;
import com.lawer.common.IpAdress;
import com.lawer.common.ResultGson;
import com.lawer.pojo.Log;
import com.lawer.pojo.User;
import com.lawer.service.LogService;
import com.lawer.service.NoticeService;
import com.lawer.service.UserService;
import org.apache.shiro.session.Session;
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
import java.util.UUID;

@Controller
@RequestMapping("notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;
    //获取律所所有的公告
    @RequestMapping("getAllNotice")
    @ResponseBody
    public String getAllNotice(String page,String limit, String key, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        User user =(User)session.getAttribute("us");
        JSONObject result = new JSONObject();
        int pageSize = Integer.parseInt(limit);
        map.put("busid",user.getBusId());
        map.put("pageSize", pageSize);
        map.put("lawerid",user.getId());
        map.put("skipCount", (Integer.parseInt(page) - 1) * pageSize);
        if (key != null) {
            JSONObject json = JSONObject.parseObject(key);
            if (!("".equals(json.get("title"))) && !(null == json.get("title"))) {
                map.put("title", json.getString("title"));
            }
            if (!("".equals(json.get("isread"))) && !(null == json.get("isread"))) {
                map.put("isread", json.getString("isread"));
            }

            if (!("".equals(json.get("create_time"))) && !(null == json.get("create_time"))) {
                String[] tricktime = json.get("create_time").toString().split(" - ");
                map.put("tricktime", tricktime[0]+ " 00:00:00");
                map.put("tricktime2", tricktime[1]+ " 23:59:59");
            }
        }

        List list = noticeService.getAllNotice(map);
        int count = noticeService.getAllNoticeCount(map);
        result.put("data",list);
        result.put("msg","请求成功");
        result.put("code", 0);
        result.put("count",count);
        return  JSON.toJSONString(result);
    }
   //添加公告
    @RequestMapping("addNotice")
    @ResponseBody
    public ResultGson addNotice(@RequestBody String json, HttpSession session, HttpServletRequest request){
        Map<String, Object> mapJson = JSON.parseObject(json);
        User user =(User)session.getAttribute("us");
        String notid =UUID.randomUUID().toString();
        mapJson.put("id", notid);
        if(Integer.parseInt((String)mapJson.get("isTop"))==1){
            int count =noticeService.selectTopCount(user.getBusId());
            if(count>=5){
                Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),2,"添加","失败", "置顶公告已满五个，添加置顶公告\""+ mapJson.get("title")+"\"失败",user.getBusId());
                logService.addLog(log);
                return ResultGson.error("置顶数已满五个，无法添加置顶公告");
            }
        }
        try{
            noticeService.addNotice(mapJson,user);
           List<Map<String,Object>> list= userService.getAllLawer(user.getBusId());
           for(Map map:list){
               String id=(String) map.get("Id");
               map.put("noticeid",notid);
               map.put("lawerid",id);
               map.put("busid",user.getBusId());
               noticeService.addUserNotice(map);
               if(!id.equals(user.getId())){
                   Map<String,Object> cmap = new HashMap<>();
                   cmap.put("id", UUID.randomUUID().toString());
                   cmap.put("lawerid",id);
                   cmap.put("descript","管理员"+user.getName()+"发布了一则新公告:"+mapJson.get("title"));
                   cmap.put("isread",0);
                   cmap.put("create_time", DateUtil.getToday());
                   noticeService.addMsg(cmap);

               }

           }

        }catch (Exception e){
            Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),2,"添加公告","失败", "添加公告\""+ mapJson.get("title")+"\"失败",user.getBusId());
            logService.addLog(log);
            return ResultGson.error("添加失败");
        }
        Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),2,"添加公告","成功", "添加公告\""+ mapJson.get("title")+"\"成功",user.getBusId());
        logService.addLog(log);
        return ResultGson.ok("添加成功");
    }

    //删除公告
    @RequestMapping("deleteNotice")
    @ResponseBody
    public ResultGson deleteNotice(String id, HttpSession session, HttpServletRequest request){
        User user =(User)session.getAttribute("us");
        Map<String,Object> map = noticeService.selectNoticeById(id);
        try{
            noticeService.deleteNotice(id);
        }catch (Exception e){
            Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),2,"删除公告","失败", "删除公告\""+ map.get("title")+"\"失败",user.getBusId());
            logService.addLog(log);
            return ResultGson.error("删除失败");
        }
        Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),2,"删除公告","成功", "删除公告\""+ map.get("title")+"\"成功",user.getBusId());
        logService.addLog(log);
        return ResultGson.ok("删除成功");
    }

    //查询公告
    @RequestMapping("selectNotice")
    @ResponseBody
    public ResultGson selectNotice(HttpSession session, HttpServletRequest request){
        User user =(User)session.getAttribute("us");
        String id=(String)session.getAttribute("noticeId");

        try{
           Map<String,Object> map =noticeService.selectNoticeById(id);
           session.setAttribute("noticeId","");
            return ResultGson.ok(map);
        }catch (Exception e){
            return ResultGson.error("查询失败");
        }

    }
    //修改公告
    @RequestMapping("updateNotice")
    @ResponseBody
    public ResultGson updateNotice(@RequestBody String json, HttpSession session, HttpServletRequest request) {
        Map<String,Object> map =JSON.parseObject(json);
        User user =(User)session.getAttribute("us");
        map.put("create_userid",user.getId());

        try {
            noticeService.updateNotice(map);
        }catch (Exception e){
            Map<String,Object> cmap = noticeService.selectNoticeById((String) map.get("id"));
            Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),2,"修改公告","失败", "修改公告\""+ cmap.get("title")+"\"失败",user.getBusId());
            logService.addLog(log);
            return ResultGson.error("修改失败");
        }
        Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),2,"修改公告","成功", "修改公告\""+ map.get("title")+"\"成功",user.getBusId());
        logService.addLog(log);
        return ResultGson.ok("修改成功");

    }
    //查询提醒消息
    @RequestMapping("selectMsg")
    @ResponseBody
    public ResultGson selectMsg(HttpSession session, HttpServletRequest request) {

        User user =(User)session.getAttribute("us");
        Map<String,Object> map = new HashMap<>();
        map.put("lawerid",user.getId());
        map.put("isread",0);
        try {
            Map<String,Object> cmap=noticeService.selectMsg(map);
            if(cmap==null){
                return ResultGson.error("暂未新消息");
            }else{
                cmap.put("isread",1);
                noticeService.updateMsg(cmap);
                return ResultGson.ok(cmap);
            }
        }catch (Exception e){
            return ResultGson.error("修改失败");
        }
    }

}

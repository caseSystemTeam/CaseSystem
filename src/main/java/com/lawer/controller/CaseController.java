package com.lawer.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawer.common.IpAdress;
import com.lawer.pojo.CaseFile;
import com.alibaba.fastjson.JSON;
import com.lawer.common.ResultGson;
import com.lawer.pojo.Indictment;
import com.lawer.pojo.Log;
import com.lawer.pojo.User;
import com.lawer.service.CaseService;
import com.lawer.service.LogService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.Serializable;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("case")
public class CaseController {

    private ObjectMapper jsonutil = new ObjectMapper();

    @Autowired
    private CaseService caseService;
    @Autowired
    private LogService logService;

    @Autowired
    private ServletContext context;

    //跳转至案件详情界面
    @RequestMapping("/getCaseId")
    @ResponseBody
    public ResultGson getCaseId(HttpSession session){
        String caseId =(String) session.getAttribute("caseId");
        String userid =(String) session.getAttribute("userid");
        Map<String,Object> map = new HashMap<>();
        map.put("caseId",caseId);
        map.put("userid",userid);
        return ResultGson.ok(map);
    }


    @RequestMapping("nextCard")
    @ResponseBody
    public ResultGson nextCard(@RequestParam("caseId") String caseId,@RequestParam("jstatus") int jstatus){
        if(jstatus==3){
            List<Indictment> list = caseService.getCaseMaxVersion(caseId);
            if(list!=null){
                if(list.get(0).getState()==1&&list.get(1).getState()==1&&list.get(2).getState()==1){
                    caseService.updateCaseJstatus(caseId,jstatus);
                    caseService.addAnjianAssist(caseId);
                }else{
                    return ResultGson.error("组员意见尚未统一，无法执行！");
                }
            }

        }else{
            caseService.updateCaseJstatus(caseId,jstatus);
        }
        return ResultGson.ok();
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
    public ResultGson addCase(@RequestBody  String json, HttpSession session, HttpServletRequest request){
        Map<String, Object> mapJson = JSON.parseObject(json);
        User user =(User)session.getAttribute("us");
       try{
           caseService.addCase(mapJson,user);
       }catch (Exception e){
           Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),1,"添加案件","失败", "添加案件\""+ mapJson.get("name")+"\"",user.getBusId());
           logService.addLog(log);
           return ResultGson.error("执行出错");
       }
        Log log =Log.ok(user.getUsername(), IpAdress.getIp(request),1,"添加案件","成功", "添加案件\""+ mapJson.get("name")+"\"",user.getBusId());
        logService.addLog(log);
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

//    @RequestMapping("sendMessage")
//    @ResponseBody
    //    public ResultGson sendMessage(@RequestParam("receiver") String receiver,
//                                  @RequestParam("message")String message,
//                                  @RequestParam("sender")String sender){

//        //解析json数组
//        List<User> receList = JSON.parseObject(receiver, new TypeReference<ArrayList<User>>(){});
//        if(receList!=null||message!=null||sender!=null){
//            try{
//                caseService.addMessage(receList,message,sender);
//            }catch (Exception e){
//                e.printStackTrace();
//                return ResultGson.error("发送失败");
//            }
//
//        }

    @RequestMapping("sendMessage")
    @ResponseBody
    public ResultGson sendMessage(@RequestParam("tomessage") String tomessage,String caseId,@RequestParam("receiver") String receiver){
        //解析json数组
        List<User> receList = JSON.parseObject(receiver, new TypeReference<ArrayList<User>>(){});
        List<Indictment> list = caseService.getCaseMaxVersion(caseId);
        int version = list.get(0).getVersion();
        Map<String,Object> map =null;
        for(int i=0;i<receList.size();i++){
            map = new HashMap<>();
            map.put("caseId",caseId);
            map.put("message",tomessage);
            map.put("version",version);
            map.put("helperId",receList.get(i).getId());
            caseService.updateCaseVersionInfo(map);
        }
        return ResultGson.ok("发送成功");
    }

    //组员回复消息的消息，并更改状态
    @RequestMapping("backMessage")
    @ResponseBody
    public ResultGson backMessage(@RequestParam("jsonp") String jsonp){
        Map<String,Object> map =  JSONObject.parseObject(jsonp,new TypeReference<Map<String, Object>>(){});
        caseService.updateCaseVersionInfo(map);
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

    @RequestMapping("getCaseInfoAssist")
    @ResponseBody
    public ResultGson getCaseInfoAssist(@RequestParam("caseId") String caseId){
        Map<String,Object> map =null;
        try{
            map = caseService.getCaseInfoAssist(caseId);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGson.error("获取补充案件信息出错");
        }

        return ResultGson.ok(map);
    }

    @RequestMapping("commitCaseResult")
    @ResponseBody
    public ResultGson commitCaseResult(@RequestParam("caseId") String caseId,
                                       @RequestParam("resultContent") String resultContent){

        Map<String,Object> map = new HashMap<>();
        map.put("id",caseId);
        map.put("resultContent",resultContent);
        try{
            caseService.updateCaseInfo(map);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGson.error("更新案件信息出错");
        }
        return ResultGson.ok();
    }

    @RequestMapping("setCaseGroup")
    @ResponseBody
    public ResultGson setCaseGroup(@RequestParam("caseId") String caseId,
                                   String member1,String member2,String member3,String member4,int jstatus){
        if(caseId==null||member1.equals("")||member2.equals("")||member3.equals("")||member4.equals("")){
            return ResultGson.error("添加失败，核心信息为空");
        }
        Map<String,String> map = new HashMap<>();
        map.put("id", UUID.randomUUID().toString());
        map.put("caseId",caseId);
        map.put("member1",member1);
        map.put("member2",member2);
        map.put("member3",member3);
        map.put("member4",member4);
        Date now=new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=dateFormat.format(now);
        map.put("time",time);
        caseService.setCaseGroup(map);

        //切换案件执行的状态
        caseService.updateCaseJstatus(caseId,jstatus);

        //以上执行完后，会切换到诉状书写部分，所以把版本1的信息也在这步添加了
        Map<String,Object> mapversinon = new HashMap<>();
        mapversinon.put("caseId",caseId);
        mapversinon.put("member1",member1);
        mapversinon.put("member2",member2);
        mapversinon.put("member3",member3);
        mapversinon.put("member4",member4);
        mapversinon.put("version",1);
        caseService.putCaseVersion(mapversinon);
        return ResultGson.ok("组员添加成功");
    }

    //获取案件的小组成员名字
    @RequestMapping("getMember")
    @ResponseBody
    public ResultGson getMember(@RequestParam("caseId") String caseId){
        Map <String,Object> map = caseService.getCaseGroup(caseId);
        return ResultGson.ok(map);
    }


    @RequestMapping("getCaseVersionInfo")
    @ResponseBody
    public ResultGson getCaseVersionInfo(@RequestParam("caseId") String caseId){
        List<Indictment> list = null;
        try{
            list = caseService.getCaseVersionInfo(caseId);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGson.error("获取案件信息出错");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("data",list);
        return ResultGson.ok(map);
    }

    @RequestMapping("addCaseVersion")
    @ResponseBody
    public ResultGson addCaseVersion(@RequestParam("caseId") String caseId){
        List<Indictment> list = caseService.getCaseMaxVersion(caseId);
        if(list!=null){
            Map<String,Object> map = new HashMap<>();
            map.put("caseId",list.get(0).getCaseId());
            map.put("member1",list.get(0).getWriterId());
            map.put("member2",list.get(0).getHelperId());
            map.put("member3",list.get(1).getHelperId());
            map.put("member4",list.get(2).getHelperId());
            map.put("version",list.get(0).getVersion()+1);
            caseService.putCaseVersion(map);
        }
        return ResultGson.ok("切换新版本成功~~");
    }

    @RequestMapping("saveInfo")
    @ResponseBody
    public ResultGson saveInfo(@RequestParam("data") String data){
        Map<String,Object> map =  JSONObject.parseObject(data,new TypeReference<Map<String, Object>>(){});
        caseService.updateAnjianAssist(map);
        return ResultGson.ok("切换新版本成功~~");
    }

    @RequestMapping("endCase")
    @ResponseBody
    public ResultGson endCase(@RequestParam("caseId") String caseId){
        Map<String,Object> map = new HashMap<>();
        map.put("p_status",1);
        map.put("id",caseId);
        caseService.updateCaseInfo(map);
        return ResultGson.ok("案件已经被结束~~");
    }

    @RequestMapping("generateWord")

    public String generateWord(@RequestParam("caseId") String caseId, HttpSession session){

       String filepath = caseService.createWord(caseId,context);
       int index = filepath.lastIndexOf("\\");
       String filename = filepath.substring(index+1);
        session.setAttribute("filepath",filepath);
        session.setAttribute("filename",filename);
        return "redirect:/comm/downloadFileByUrl";
    }






}

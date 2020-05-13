package com.lawer.controller;

import com.lawer.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * 页面跳转
 * @author 张涛
 * date: 2020.3.31 17:33:24
 */
@Controller
@RequestMapping("page")
public class PageController {
    //跳转到案件登记页面
    @RequestMapping("addcase")
    public String Login(){
        return "html/addcase";
    }

    //跳转到全部案件页面
    @RequestMapping("allcase")
    public String toAllCase(){
        return "html/allcase";
    }

    //跳转到未完成案件页面
    @RequestMapping("unfinishcase")
    public String toUnfinishCase(){
        return "html/unfinishcase";
    }

    //跳转到已完成案件页面
    @RequestMapping("finishcase")
    public String tofinishCase(){
        return "html/finishcase";
    }

    //跳转到个人信息页面
    @RequestMapping("peopleinfo")
    public String toPeopleInfo(){
        return "html/peopleinfo";
    }

    //跳转到员工信息页面
    @RequestMapping("allpeople")
    public String toAllPeople(){
        return "html/allpeople";
    }

    //跳转到添加员工页面
    @RequestMapping("addpeople")
    public String toAddPeople(){
        return "html/addpeople";
    }

    //跳转到登录日志页面
    @RequestMapping("loginrecord")
    public String toLoginRecord(){
        return "html/loginrecord";
    }

    //跳转到操作日志页面
    @RequestMapping("operaterecord")
    public String toOperateRecord(){
        return "html/operaterecord";
    }

    //跳转到修改密码页面
    @RequestMapping("modifyPassword")
    public String toModifyPassword(){
        return "html/modify_password";
    }

    //跳转到修改密码页面
    @RequestMapping("updateInfo")
    public String toUpdateInfo(String id, HttpSession session){
        if(id!=null && !"".equals(id)){
            session.setAttribute("mdfId",id);
        }else{
            User user =(User) session.getAttribute("us");
            session.setAttribute("mdfId",user.getId());
        }
        return "html/updateInfo";
    }
    //跳转到修改密码页面
    @RequestMapping("casegroup")
    public String toCaseGroup(String id, HttpSession session){

        return "html/casegroup";
    }

    //跳转至案件详情界面
    @RequestMapping("/tocase")
    public String toCasePlan(String caseId, String lawerid,HttpSession session){
        User user=(User) session.getAttribute("us");
        String userid = user.getId();
//        System.out.println("当前案件id"+caseId);
//        System.out.println("负责人id"+lawerid);
//        System.out.println("用户id"+userid);
        session.setAttribute("caseId",caseId);
        session.setAttribute("userid",userid);
        //如果当前用户是案件的负责人，转入负责人的界面
        if(lawerid.equals(userid)){
            return "/html/casePlan";
        }else{
            return "/html/casePlanAssist"; //否则转入协助界面
        }

    }

    //跳转到忘记密码页面
    @RequestMapping("forgetPs")
    public String toforgetPs(){

        return "html/forgetps";
    }

}

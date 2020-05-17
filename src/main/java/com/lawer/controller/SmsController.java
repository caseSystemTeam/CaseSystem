package com.lawer.controller;

import com.lawer.common.ResultGson;


import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 短信服务控制类
 */
@Controller
@RequestMapping("/sms")
public class SmsController {
    private String apiUrl = "https://sms_developer.zhenzikj.com";
    private String appId = "105685";
    private String appSecret = "9c44e25c-8a85-43ed-919d-c8f41a09938f";

    @RequestMapping("sendSms")
    @ResponseBody
    public ResultGson SendSms(String phonenumber,HttpSession session){
        //生成6位验证码
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        //发送短信
        ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
        String message="您的验证码为"+verifyCode+",该验证码五分钟内有效，请勿泄露于他人。如非本人操作，请忽略此短信。";
          try{
              client.send(phonenumber,message);
              session.setAttribute("code",verifyCode);
          }catch (Exception e){
              return ResultGson.error("发送失败");
          }

          return ResultGson.ok();
    }

}

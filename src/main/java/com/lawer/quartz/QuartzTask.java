package com.lawer.quartz;

import com.lawer.pojo.User;
import com.lawer.service.CaseListService;
import com.lawer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务
 */
@Component
public class QuartzTask {
    @Autowired
    private UserService userService;
    @Autowired
    private CaseListService caseListService;
    //按时更新律师已完成和未完成案件数
    @Scheduled(cron = "0 */1 * * * ? ")    //每小时执行一次
    public void updateCaseCount() {
        System.out.println("zzzzzzzzz");
        List<Map<String,Object>> list= userService.getAllBusiness();
        Map<String,Object> cmap = new HashMap<>();

        for(Map<String,Object> map:list){
            List<Map<String,Object>> lawerList = userService.getAllLawer((String) map.get("Id"));
            cmap.put("busId", map.get("Id"));
            for(Map<String,Object> lmap:lawerList){
               int scount=0;
               int ucount=0;
                cmap.put("jstatus",0);
                cmap.put("lawerid",lmap.get("Id"));
                scount= caseListService.getCaseCount(cmap);
                cmap.put("jstatus",1);
                ucount= caseListService.getCaseCount(cmap);
                if(scount!=(int)lmap.get("solve")||ucount!=(int)lmap.get("unsolve")){
                    User user = new User();
                    user.setId((String) lmap.get("Id"));
                    user.setSolve((int)lmap.get("solve"));
                    user.setUnsolve((int)lmap.get("unsolve"));
                    userService.upinfor(user);

                }


            }
        }


    }

}

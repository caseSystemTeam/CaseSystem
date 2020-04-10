package com.lawer.controller;

import com.lawer.common.FileConvert;
import com.lawer.pojo.CaseFile;
import com.lawer.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.lawer.common.FileUpAndDown;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("comm")
public class CommonController {

    @Autowired
    CommonService commonService;

    @RequestMapping("/SingleUpload")
    public String SingleUpload(@RequestParam("caseId") String caseId, @RequestParam("uploadfile") List<MultipartFile> uploadfile
            , HttpServletRequest request){
        FileUpAndDown fileCon = new FileUpAndDown();
        CaseFile lf = fileCon.fileUpLoad(caseId, uploadfile, request);

        if(lf!=null) {
            commonService.addFile(lf);
        }
        return "forward:userCon/toindex";
    }

    @RequestMapping("/WatchFile")
    @ResponseBody
    public String watchFile(String filepath,String filename){
        //根据\找到当前文件所在文件夹的目录
        int index = filepath.lastIndexOf("\\");
        String path = filepath.substring(0,index+1);
        System.out.println("切割后的字符串："+path);
        path = path.replace("\\","\\\\");
        System.out.println("转变后的路径："+path);

        FileConvert util = new FileConvert();
        try{
            util.WordTOHtml(path,filename);
        }catch (Exception e){
            System.out.println("文件转换出错");
        }
        return "kekeke";
    }


}

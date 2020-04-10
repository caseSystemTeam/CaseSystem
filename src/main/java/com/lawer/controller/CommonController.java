package com.lawer.controller;

import com.lawer.common.FileConvert;
import com.lawer.common.ResultGson;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("comm")
public class CommonController {

    @Autowired
    CommonService commonService;

    @RequestMapping("/SingleUpload")
    @ResponseBody
    public String SingleUpload(@RequestParam("caseId") String caseId, @RequestParam("uploadfile") List<MultipartFile> uploadfile
            , HttpServletRequest request){
        FileUpAndDown fileCon = new FileUpAndDown();
        CaseFile lf = fileCon.fileUpLoad(caseId, uploadfile, request);
        String result = null;
        if(lf!=null) {
            commonService.addFile(lf);
            result = ResultGson.ok("文件上传成功！！").toJson();
        }else{
            result = ResultGson.error("文件上传失败，请检查文件格式是否符合要求。").toJson();
        }
        return result;
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

        String json = null;
        FileConvert util = new FileConvert();
        Map<String,Object> map = new HashMap<>();
        try{
            //文件转换，返回结果是新文件的存储路径
            String fileHtml = util.WordTOHtml(path,filename);
            map.put("fileHtml",fileHtml);
            json = ResultGson.ok(map).toJson();
        }catch (Exception e){
            json = ResultGson.error("文件转换出错，请检查文件是否是标准word、excel、pdf格式").toJson();
            //System.out.println("文件转换出错");
        }
        return json;
    }


}

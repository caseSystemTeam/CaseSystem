package com.lawer.controller;

import com.lawer.common.FileConvert;
import com.lawer.common.PoiExcelToHtmlUtil;
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
        String targetPath = "D:\\upload\\";
        CaseFile lf = fileCon.fileUpLoad(caseId, uploadfile, request,targetPath);
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
    public String watchFile(String filepath,String fileid){
        //根据\找到当前文件所在文件夹的目录
        int index = filepath.lastIndexOf("\\");
        //根据URL获取文件根路径
        String rootpath = filepath.substring(0,index+1);
        //需要根据操作系统来配置
        rootpath = rootpath.replace("\\","\\\\");

        //根据'.'确认文件后缀名
        int index2 = filepath.lastIndexOf(".");
        String fileFormat = filepath.substring(index2+1,filepath.length()); //左闭又开

        String json = null; //向前台返回的josn格式信息
        FileConvert util = new FileConvert(); //文件转换类
        Map<String,Object> map = new HashMap<>();
        String filePathHtml =null;  //html文件地址

        //需要跟配置文件的相匹配，暂时写在这里
        String targetPath = "D:\\upload\\";

        //根据文件后缀名确定转换方式
        try{
            if("doc".equals(fileFormat)){
                //文件转换，返回结果是新文件的存储路径
                filePathHtml= util.WordTOHtml(rootpath,filepath,targetPath,fileid);
                //filePathHtml= util.WordTOHtml(rootpath,filepath,targetPath,"哈哈哈哈哈哈");
                map.put("filePathHtml",filePathHtml);
                map.put("type","office");
            }else if("docx".equals(fileFormat)){
                //文件转换，返回结果是新文件的存储路径
                filePathHtml = util.WordTOHtmlDocx(filepath,targetPath,fileid);
                map.put("filePathHtml",filePathHtml);
                map.put("type","office");
            }else if ("txt".equals(fileFormat)||"pdf".equals(fileFormat)||"jpg".equals(fileFormat)||"png".equals(fileFormat)||"jpeg".equals(fileFormat)){
                //如果是txt、jpg、pdf这种浏览器支持浏览的格式，则切割出文件名，然后返回/upload/文件名的路径
                int index3 = filepath.lastIndexOf("\\");
                filePathHtml = "/upload/"+filepath.substring(index3+1,filepath.length());
                map.put("filePathHtml",filePathHtml);
                map.put("type","common");
            }else if ("xls".equals(fileFormat)||"xlsx".equals(fileFormat)){
                //filePathHtml = util.excelToHtml(rootpath,filepath,targetPath,fileid);
                filePathHtml = PoiExcelToHtmlUtil.excelToHtml(filepath,fileid,targetPath);
                map.put("filePathHtml",filePathHtml);
                map.put("type","office");
            }
            else{
                map.put("type","nosupport");
                json = ResultGson.ok(map).toJson();
                return json;
            }
        }catch (Exception e){
            e.printStackTrace();
            json = ResultGson.error("文件转换出错，请检查文件是否是标准word、excel格式").toJson();
            return json;
        }
        //将新转换的文件路径信息封装到返回信息中
        //map中的type，封装了返回url的类型
        // office说明是word、excel，则返回值是对应的/upload/文件名.html
        //txt类型，则直接返回服务器上的txt文件
        json = ResultGson.ok(map).toJson();
        return json;
    }


}

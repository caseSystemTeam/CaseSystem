package com.lawer.controller;

import com.lawer.pojo.CaseFile;
import com.lawer.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
}

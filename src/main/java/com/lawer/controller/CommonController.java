package com.lawer.controller;

import com.lawer.pojo.CaseFile;
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

    @RequestMapping("/SingleUpload")
    public String SingleUpload(@RequestParam("caseId") String caseId, @RequestParam("uploadfile") List<MultipartFile> uploadfile
            , HttpServletRequest request){

        FileUpAndDown fileCon = new FileUpAndDown();
        int id = Integer.valueOf(caseId);
        CaseFile lf = fileCon.fileUpLoad(id, uploadfile, request);

        if(lf!=null) {
            //lawfileService.addFile(lf);
        }
        return "forward:anjianProgress.action?id="+id;
    }
}

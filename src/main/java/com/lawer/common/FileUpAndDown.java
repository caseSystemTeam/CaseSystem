package com.lawer.common;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lawer.pojo.CaseFile;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FileUpAndDown {

	public CaseFile fileUpLoad(String caseId, List<MultipartFile> uploadfile, HttpServletRequest request,String dirPath) {
		//判断上传的文件是否存在
			String originalFileName;
			//String dirPath ;
			String newFileName;
			String fileurl = null;
			CaseFile lf=null;
				if(!uploadfile.isEmpty()) {
						lf = new  CaseFile();
						for(MultipartFile file:uploadfile){
							originalFileName = file.getOriginalFilename();
							//设置上传文件的保存地址目录
							File filePath = new File(dirPath);
							//如果目标地址不存在，就创建这个地址
							if(!filePath.exists()) {
								boolean flag = filePath.mkdirs();
								if(!flag){
									//如果没有创建成功
									return null;
								}
							}
							//构建新的文件名字
							newFileName = caseId+"_"+UUID.randomUUID()+"_"+originalFileName;
							try {
								//上传至指定位置
								fileurl = dirPath+newFileName ;
								file.transferTo(new File(fileurl));
								//构建返回数据
								lf.setFileid(UUID.randomUUID().toString());
								lf.setCaseId(caseId);
								lf.setFilename(originalFileName);
								lf.setUrl(fileurl);
								Date now=new Date();
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								String time=dateFormat.format(now);
								lf.setCreateTime(time);
							}catch(Exception e) {
								e.printStackTrace();
								
							}
						
						}
						return lf;
				}else {
					return null;
				}
	}

    public void fileDownload(File file, HttpServletResponse response,String filename){

        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
}

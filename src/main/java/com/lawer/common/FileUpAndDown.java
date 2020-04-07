package com.lawer.common;


import javax.servlet.http.HttpServletRequest;
import com.lawer.pojo.CaseFile;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class FileUpAndDown {

	public CaseFile fileUpLoad(int id, List<MultipartFile> uploadfile, HttpServletRequest request) {
		//判断上传的文件是否存在
			String originalFileName;
			String dirPath ;
			String newFileName;
			String fileurl = null;
			CaseFile lf=null;
				if(!uploadfile.isEmpty()) {
						lf = new  CaseFile();
						for(MultipartFile file:uploadfile){
							originalFileName = file.getOriginalFilename();
							//设置上传文件的保存地址目录
							dirPath = request.getServletContext().getRealPath("/upload/");
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
							newFileName = String.valueOf(id)+"_"+UUID.randomUUID()+"_"+originalFileName;
							try {
								//上传至指定位置
								fileurl = dirPath+newFileName ;
								file.transferTo(new File(fileurl));
								//构建返回数据
								lf.setFielid(id);
								lf.setFilename(originalFileName);
								lf.setUrl(fileurl);
							}catch(Exception e) {
								e.printStackTrace();
								
							}
						
						}
						return lf;
				}else {
					return null;
				}
	}
	
//	public ResponseEntity<byte []> fileDownLoad(HttpServletRequest request,String filename,String path) throws IOException{
//
//				//创建该文件对象
//				File file = new File(path+File.separator+filename);
//				//对文件名编码，防止中文乱码问题
//				//filename = this.getFilename(request,filename);
//
//				//设置响应头
//				HttpHeaders headers = new HttpHeaders();
//				//通知浏览器以下载的方式打开文件
//				headers.setContentDispositionFormData("attachment", filename);
//				//定义以流的方式下载返回文件数据
//				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//
//				return new ResponseEntity<byte []> (FileUtils.readFileToByteArray(file),headers,HttpStatus.OK);
//
//	}
	
}

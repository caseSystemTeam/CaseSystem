package com.lawer.mapper;

import com.lawer.pojo.CaseFile;

import java.util.List;



public interface CommonMapper {
	
	//新添加的关于文件路径操作
	List<CaseFile> getFileById(int fileid);
	void addFile(CaseFile file);
	void deleteFileUrlByFileName(String filename);
}

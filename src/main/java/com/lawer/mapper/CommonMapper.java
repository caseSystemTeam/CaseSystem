package com.lawer.mapper;

import com.lawer.pojo.CaseFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;



public interface CommonMapper {
	
	//新添加的关于文件路径操作
	void addFile(CaseFile file);
	void deleteFileUrlByFileName(String filename);
}

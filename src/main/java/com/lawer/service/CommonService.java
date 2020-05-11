package com.lawer.service;

import java.util.List;
import com.lawer.pojo.CaseFile;

public interface CommonService {

	//新添加的关于文件路径操作

		void addFile(CaseFile file);
		void deleteFileUrlByFileName(String filename);
}

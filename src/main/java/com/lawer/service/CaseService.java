package com.lawer.service;

import com.lawer.pojo.CaseFile;
import com.lawer.pojo.User;
import java.util.Map;
import java.util.List;

public interface CaseService {

	List<CaseFile> getFileAll(String caseId);
	CaseFile getFileById(String fileid);
	void deleteFileById(String fileid);


	/**
	 * 案件管理业务层接口
	 */
    public void addCase(Map<String,Object> map, User user); //添加案件
	public Map<String,Object> getCaseInfo(String caseId); //添加案件

	void addMessage(List<User> receList,String info,String sender);


}

package com.lawer.service;

import com.lawer.pojo.CaseFile;
import com.lawer.pojo.Indictment;
import com.lawer.pojo.User;

import javax.servlet.ServletContext;
import java.util.Map;
import java.util.List;

public interface CaseService {

	List<CaseFile> getFileAll(String caseId); //获取所有文件
	CaseFile getFileById(String fileid); //获取单个文件
	void deleteFileById(String fileid);


	/**
	 * 案件管理业务层接口
	 */
    public void addCase(Map<String,Object> map, User user); //添加案件
	public Map<String,Object> getCaseInfo(String caseId); //获取当前案件信息
	public Map<String,Object> getCaseInfoAssist(String caseId); //添加当前案件补充信息

	public void addMessage(List<User> receList,String info,String sender);
	public void setCaseGroup(Map<String,String> map);
	public Map<String,Object> getCaseGroup(String caseId);
	public void putCaseVersion(Map<String,Object> map);
	public void updateCaseVersionInfo(Map<String,Object> map);  //更新案件版本的一条信息
	public List<Indictment> getCaseVersionInfo(String caseId);
	public List<Indictment> getCaseMaxVersion(String caseId);
	public void updateCaseInfo(Map<String,Object> map);
	public void updateCaseJstatus(String caseId,int jstatus);
	public void addAnjianAssist(String caseId);
	public void updateAnjianAssist(Map<String,Object> map);
	public String createWord(String caseId, ServletContext context);  //生成word文件

}

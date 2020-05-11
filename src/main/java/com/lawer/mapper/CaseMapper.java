package com.lawer.mapper;

import com.lawer.pojo.CaseFile;
import com.lawer.pojo.Indictment;
import com.lawer.pojo.Message;
import org.apache.ibatis.annotations.Param;
import java.util.Map;
import java.util.List;


public interface CaseMapper {

	List<CaseFile> getFileAll(String caseId);
	CaseFile getFileById(String fileid);
	void deleteFileById(String fileid);

	/**
	 * 案件管理数据层
	 */
	public void addCase(@Param("map") Map<String, Object> map); //添加案件
	public Map<String,Object> getCaseInfo(String caseId); //获得当前案件的信息
	public Map<String,Object> getCaseInfoAssist(String caseId); //获得当前案件的信息
    public void updateCaseInfo(@Param("map")Map<String,Object> map); //更新当前案件信息
	public void addMessage(List<Message> list); //添加消息
	void setCaseGroup(@Param("map")Map<String,String> map);  //设置案件的小组成员
	Map<String,Object> getCaseGroup(String caseId);  //设置案件的小组成员
	void putCaseVersion(@Param("map")Map<String,Object> map);  //设置案件版本
	public List<Indictment> getCaseVersionInfo(String caseId); //获得案件的版本列表
	public List<Indictment> getCaseMaxVersion(String caseId);  //获取当前案件的版本最大值
    public void addAnjianAssist(String caseId); //向案件副表插入一条当前案件id的初始数据
    public void updateAnjianAssist(@Param("map")Map<String,Object> map); //更新案件附表的方法

}

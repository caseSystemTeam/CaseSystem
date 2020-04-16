package com.lawer.mapper;

import com.lawer.pojo.CaseFile;
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
	public Map<String,Object> getCaseInfo(String caseId);
	public void addMessage(List<Message> list);

}

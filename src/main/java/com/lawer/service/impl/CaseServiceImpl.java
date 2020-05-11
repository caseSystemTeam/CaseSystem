package com.lawer.service.impl;




import com.lawer.mapper.CaseMapper;
import com.lawer.pojo.CaseFile;
import com.lawer.pojo.Indictment;
import com.lawer.pojo.Message;
import com.lawer.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.lawer.common.DateUtil;
import com.lawer.pojo.User;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CaseServiceImpl implements CaseService {

	@Autowired
	private CaseMapper mapper;

	/*
		获取当前案件下的所有文件
	 */
	@Override
	public List<CaseFile> getFileAll(String caseId) {
		List<CaseFile> list = mapper.getFileAll(caseId);
		return list;
	}

	/*
	根据文件id，查询对应的单个文件
	 */
	@Override
	public CaseFile getFileById(String fileid) {
		CaseFile cf = mapper.getFileById(fileid);
		return cf;
	}

	@Override
	public void deleteFileById(String fileid) {
		mapper.deleteFileById(fileid);
	}
	
	/**
     * 添加案件
     * @param map
     */
    public void addCase(Map<String, Object> map, User user) {
        String id = UUID.randomUUID().toString();
        String rtime = DateUtil.getToday();
        String busId = user.getBusId();
        map.put("id",id);
        map.put("rtime",rtime);
        map.put("busId",busId);
        map.put("fstatus",1);
        map.put("jstatus",0);
        map.put("p_status",2);
		mapper.addCase(map);


    }

	@Override
	public Map<String,Object> getCaseInfo(String caseId) {
		Map<String,Object> map = null;
		map = mapper.getCaseInfo(caseId);
		return map;
	}

	@Override
	public Map<String,Object> getCaseInfoAssist(String caseId) {
		Map<String,Object> map = null;
		map = mapper.getCaseInfoAssist(caseId);
		return map;
	}

    public void updateCaseInfo(Map<String,Object> map){
        mapper.updateCaseInfo(map);
    }


	public void addMessage(List<User> receList,String info,String sender){
		List<Message> list = new ArrayList<>();
		Message message = null;
		for(int i=0;i<receList.size();i++){
			message = new Message();
			message.setId(UUID.randomUUID().toString());
			message.setReceiver(receList.get(i).getId());
			message.setInfo(info);
			message.setSender(sender);
			message.setIsRead(0);
			Date now=new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=dateFormat.format(now);
			message.setSendTime(time);
			list.add(message);
		}
		mapper.addMessage(list);
	}

	public void setCaseGroup(Map<String,String> map){
		mapper.setCaseGroup(map);
	}

	public Map<String,Object> getCaseGroup(String caseId){
		Map<String,Object> map = mapper.getCaseGroup(caseId);
		return map;
	}

	public void putCaseVersion(Map<String,Object> map){
		Map<String,Object> temp = new HashMap<>();
		temp.put("id",UUID.randomUUID().toString());
		temp.put("caseId",map.get("caseId"));
		temp.put("writerId",map.get("member1"));
		temp.put("helperId",map.get("member2"));
		temp.put("version",map.get("version"));
		mapper.putCaseVersion(temp);

		Map<String,Object> temp2 = new HashMap<>();
		temp2.put("id",UUID.randomUUID().toString());
		temp2.put("caseId",map.get("caseId"));
		temp2.put("writerId",map.get("member1"));
		temp2.put("helperId",map.get("member3"));
		temp2.put("version",map.get("version"));
		mapper.putCaseVersion(temp2);

		Map<String,Object> temp3 = new HashMap<>();
		temp3.put("id",UUID.randomUUID().toString());
		temp3.put("caseId",map.get("caseId"));
		temp3.put("writerId",map.get("member1"));
		temp3.put("helperId",map.get("member4"));
		temp3.put("version",map.get("version"));
		mapper.putCaseVersion(temp3);
	}

	//获取当前案件的版本信息
	public List<Indictment> getCaseVersionInfo(String caseId){
		List<Indictment> list = mapper.getCaseVersionInfo(caseId);
		return list;
	}

	//获取当前案件最大的执行版本
	public List<Indictment> getCaseMaxVersion(String caseId){
		List<Indictment> list = mapper.getCaseMaxVersion(caseId);
		return list;
	}

	public void updateCaseJstatus(String caseId,int jstatus){

		Map<String,Object> map = new HashMap<>();
		map.put("id",caseId);
		map.put("jstatus",jstatus+1);
		mapper.updateCaseInfo(map);

	}

	public void addAnjianAssist(String caseId){
		mapper.addAnjianAssist(caseId);
	}
	public void updateAnjianAssist(Map<String,Object> map){
		mapper.updateAnjianAssist(map);
	}


}

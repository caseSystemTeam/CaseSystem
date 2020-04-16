package com.lawer.service.impl;




import com.lawer.mapper.CaseMapper;
import com.lawer.pojo.CaseFile;
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
	@Override
	public List<CaseFile> getFileAll(String caseId) {
		List<CaseFile> list = mapper.getFileAll(caseId);
		return list;
	}

	@Override
	public CaseFile getFileById(String fileid) {
		return null;
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



}

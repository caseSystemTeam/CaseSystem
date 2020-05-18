package com.lawer.service.impl;




import com.lawer.common.ExportMyWord;
import com.lawer.mapper.CaseMapper;
import com.lawer.mapper.UserMapper;
import com.lawer.pojo.CaseFile;
import com.lawer.pojo.Indictment;
import com.lawer.pojo.Message;
import com.lawer.service.CaseService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.lawer.common.DateUtil;
import com.lawer.pojo.User;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;

@Service
@Transactional
public class CaseServiceImpl implements CaseService {

	@Autowired
	private CaseMapper mapper;

	@Autowired
	private UserMapper userMapper;

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
        map.put("cid",user.getId());
        map.put("cname",user.getName());
        map.put("busId",busId);
        map.put("fstatus",1);
        map.put("jstatus",1);
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

	public void updateCaseVersionInfo(Map<String,Object> map){
    	mapper.updateCaseVersionInfo(map);
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

	//生成word文件
	public String createWord(String caseId, ServletContext context){
		Map<String,Object> datamap = new HashMap<>();
		ExportMyWord emw = new ExportMyWord();
		//案件信息的数据
		Map<String,Object> map1 = mapper.getCaseInfo(caseId);
		datamap.put("ajname",map1.get("name"));
		//根据用户id查询名字
		String name = userMapper.nameById((String)map1.get("lawerid"));
		datamap.put("ajlawerid",name);
		datamap.put("ajrtime",map1.get("rtime"));
		datamap.put("ajmoney",map1.get("money"));
		datamap.put("ajcusname",map1.get("cusname"));
		datamap.put("ajcustelphone",map1.get("cus_telphone"));
		//将html内容提取为纯文本
		String tempContent = (String) map1.get("content");
		tempContent = tempContent.replaceAll("\\<.*?\\>","");
		tempContent = StringEscapeUtils.unescapeHtml4(tempContent);
		datamap.put("ajcontent",tempContent);
		String tempResultContent = (String)map1.get("resultContent");
		tempResultContent = tempResultContent.replaceAll("\\<.*?\\>","");
		tempResultContent = StringEscapeUtils.unescapeHtml4(tempResultContent);
		datamap.put("ajresultContent",tempResultContent);


		//小组成员的信息
		Map<String,Object> map2 = mapper.getCaseGroup(caseId);
		datamap.put("user1",name);
		String name2 = userMapper.nameById((String)map2.get("member2"));
		String name3 = userMapper.nameById((String)map2.get("member3"));
		String name4 = userMapper.nameById((String)map2.get("member4"));
		datamap.put("user2",name2);
		datamap.put("user3",name3);
		datamap.put("user4",name4);

		//案件版本信息
		List<Indictment> list = mapper.getCaseVersionInfo(caseId);
		List<Map<String,Object>> IndiList = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			Indictment ind = list.get(i);
			Map<String,Object> tempmap = new HashMap<>();
			tempmap.put("version",ind.getVersion());
			if(map2.get("member2").equals(ind.getHelperId())){
				tempmap.put("helperid",name2);
			}else if(map2.get("member3").equals(ind.getHelperId())){
				tempmap.put("helperid",name3);
			}else if(map2.get("member4").equals(ind.getHelperId())){
				tempmap.put("helperid",name4);
			}
			if(ind.getState()==0){
				tempmap.put("state","不同意");
			}else if(ind.getState()==1){
				tempmap.put("state","同意");
			} else if(ind.getState()==3){
				tempmap.put("state","未处理");
			}
			String message = ind.getMessage();
			if(message==null||message.equals("")){
				message = "  ";
			}
			tempmap.put("message",message);
			String idear = ind.getIdear();
			if(idear==null||idear.equals("")){
				idear = "  ";
			}
			tempmap.put("idear",idear);
			IndiList.add(tempmap);
		}
		datamap.put("indictment",IndiList);

		//立案审理信息
		Map<String,Object> map3 = mapper.getCaseInfoAssist(caseId);
		datamap.put("lianinfo",map3.get("lian_info"));
		datamap.put("lianfaguan",map3.get("lian_faguan"));
		datamap.put("liannumber",map3.get("lian_number"));
		datamap.put("liandidian",map3.get("lian_didian"));
		datamap.put("liantimestart",map3.get("lian_timestart"));
		datamap.put("liantimeend",map3.get("lian_timeend"));
		datamap.put("ktinfo",map3.get("kt_info"));
		datamap.put("kttimestart",map3.get("kt_timestart"));
		datamap.put("kttimeend",map3.get("kt_timeend"));
		datamap.put("tsinfo",map3.get("ts_info"));
		datamap.put("tstime",map3.get("ts_time"));

		String filepath = "D:\\upload\\"+map1.get("name")+".doc";
		emw.createWord(datamap,"temp.ftl",filepath,context);

		return filepath;
	}

}

package com.lawer.service.impl;




import com.lawer.mapper.CaseMapper;
import com.lawer.pojo.CaseFile;
import com.lawer.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.lawer.common.DateUtil;
import com.lawer.pojo.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

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
		caseMapper.deleteFileById(fileid);
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

}

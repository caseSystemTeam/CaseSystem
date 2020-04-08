package com.lawer.service.impl;

import com.lawer.common.DateUtil;
import com.lawer.mapper.CaseMapper;
import com.lawer.pojo.User;
import com.lawer.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

/**
 * 案件管理业务层实现类
 */
@Service
@Transactional
public class CaseServiceImpl implements CaseService {

    @Autowired
    private CaseMapper caseMapper;

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
        caseMapper.addCase(map);


    }
}

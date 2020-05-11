package com.lawer.service.impl;

import com.lawer.mapper.CaseListMapper;
import com.lawer.service.CaseListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CaseListServiceImpl implements CaseListService {
    @Autowired
    private CaseListMapper caseListMapper;
    //查询律所所有案件信息
    public List<Map<String, Object>> getAllCase(Map<String, Object> map) {
        List<Map<String,Object>> list =caseListMapper.getAllCase(map);
        for(Map<String,Object> cmap:list){
            cmap.put("lawername",cmap.get("lawername")+"("+cmap.get("position")+")");
        }
        return list;
    }

    //获取当前律所的案件数
    public int getACaseCount( Map<String,Object> map){

        return caseListMapper.getACaseCount(map);
    }

    @Override
    public List<Map<String, Object>> getCaseById(Map<String, Object> map) {
        List<Map<String,Object>> list =caseListMapper.getCaseById(map);
        for(Map<String,Object> cmap:list){
            cmap.put("lawername",cmap.get("lawername")+"("+cmap.get("position")+")");
        }
        return list;
    }

    @Override
    public int getCaseCount(Map<String, Object> map) {

        return caseListMapper.getCaseCount(map);
    }

    @Override
    public void transferPerson(Map<String, Object> map) {
        caseListMapper.updateCase(map);
    }

    @Override
    public List<Map<String, Object>> getGroupCase(Map<String, Object> map) {
        List<Map<String,Object>> list =caseListMapper.getGroupCase(map);
        for(Map<String,Object> cmap:list){
            cmap.put("lawername",cmap.get("lawername")+"("+cmap.get("position")+")");
        }
        return null;
    }

    @Override
    public int getGroupCaseCount(Map<String, Object> map) {
        return caseListMapper.getGroupCaseCount(map);
    }
}

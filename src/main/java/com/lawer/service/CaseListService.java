package com.lawer.service;

import java.util.List;
import java.util.Map;

public interface CaseListService {
    //获取所有案件
    public List<Map<String,Object>> getAllCase(Map<String,Object> map);
    //获取当前律所的案件数
    public int getACaseCount(Map<String,Object> map);
    //获取所有案件
    public List<Map<String,Object>> getCaseById(Map<String,Object> map);
    //获取当前个人的案件数
    public int getCaseCount(Map<String,Object> map);
    //转移案件
    public void transferPerson(Map<String,Object> map);
}

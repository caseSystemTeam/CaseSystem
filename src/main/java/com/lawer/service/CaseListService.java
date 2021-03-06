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
    //获取小组案件信息
    public List<Map<String,Object>> getGroupCase(Map<String,Object> map);
    //获取小组的案件数
    public int getGroupCaseCount(Map<String,Object> map);

    /**
     * 通过案件id删除案件
     * @param id
     * @return
     */
    void deleteCase(String id);

    /**
     * 通过案件id查询案件信息
     * @param id
     * @return
     */
    Map<String,Object> SelectCaseById(String id);

    //获取未分配案件
    public List<Map<String,Object>> getUnallocation(Map<String,Object> map);
    //获取未分配案件数量
    public int getUnallocationCount( Map<String,Object> map);

    //获取当前律所的案件数
    public void updateCase(Map<String,Object> map);
}

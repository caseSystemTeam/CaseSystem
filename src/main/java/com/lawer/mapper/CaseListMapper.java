package com.lawer.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CaseListMapper {
    //获取所有案件信息
    public List<Map<String,Object>> getAllCase(@Param("map") Map<String,Object> map);

    //获取当前律所的案件数
    public int getACaseCount(@Param("map") Map<String,Object> map);

    //获取所有案件
    public List<Map<String,Object>> getCaseById(@Param("map") Map<String,Object> map);

    //获取当前个人的案件数
    public int getCaseCount(@Param("map") Map<String,Object> map);

    //修改案件信息
    public void updateCase(@Param("map") Map<String,Object> map);

    //获取小组案件信息
    public List<Map<String,Object>> getGroupCase(@Param("map") Map<String,Object> map);
    //获取小组的案件数
    public int getGroupCaseCount(@Param("map") Map<String,Object> map);

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
}

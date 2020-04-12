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
}

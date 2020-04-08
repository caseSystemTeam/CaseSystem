package com.lawer.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 案件管理数据层
 */
public interface CaseMapper {
    public void addCase(@Param("map") Map<String, Object> map); //添加案件
}

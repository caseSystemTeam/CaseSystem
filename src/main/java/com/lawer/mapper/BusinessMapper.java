package com.lawer.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BusinessMapper {
    public Map<String,Object> getBusinessById(String id);  //通过id查询企业信息
    public List<Map<String, Object>> getBusinessUser(@Param("map") Map<String, Object> map); //获取公司当前所有员工信息
}

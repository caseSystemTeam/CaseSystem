package com.lawer.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BusinessMapper {
    public Map<String,Object> getBusinessById(String id);  //通过id查询企业信息
    public List<Map<String, Object>> getBusinessUser(@Param("map") Map<String, Object> map); //获取公司当前所有员工信息
    public int getBusUserCount(@Param("map") Map<String,Object> map);  //获取员工数量
    public void deleteUser(String id); //删除用户
    public List<Map<String,Object>> getAllBusiness(); //获取所有律所信息
}

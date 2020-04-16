package com.lawer.mapper;

import com.lawer.pojo.Log;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LogMapper {

    public void addLog(Log log);  //添加日志

    public List<Map<String,Object>> getAllLog(@Param("map") Map<String,Object> map);  //根据类型获取日志

    public int getLogCount(@Param("map") Map<String,Object> map);  //根据类型获取日志

}

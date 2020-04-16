package com.lawer.service;

import com.lawer.pojo.Log;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LogService {

    public void addLog(Log log);  //添加日志

    public List<Map<String,Object>> getAllLog(Map<String,Object> map);  //根据类型获取日志

    public int getLogCount(Map<String,Object> map);  //根据类型获取日志
}

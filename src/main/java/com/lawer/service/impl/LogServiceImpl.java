package com.lawer.service.impl;

import com.lawer.mapper.LogMapper;
import com.lawer.pojo.Log;
import com.lawer.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Autowired
     private LogMapper logMapper;
    @Override
    public void addLog(Log log) {
       logMapper.addLog(log);
    }

    @Override
    public List<Map<String,Object>> getAllLog(Map<String, Object> map) {
        return logMapper.getAllLog(map);
    }

    @Override
    public int getLogCount(Map<String, Object> map) {

        return logMapper.getLogCount(map);
    }

    @Override
    public void deleteLog(String id) {
        logMapper.deleteLog(id);
    }
}

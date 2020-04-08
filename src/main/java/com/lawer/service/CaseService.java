package com.lawer.service;

import com.lawer.pojo.User;

import java.util.Map;

/**
 * 案件管理业务层接口
 */
public interface CaseService {

    public void addCase(Map<String,Object> map, User user); //添加案件

}

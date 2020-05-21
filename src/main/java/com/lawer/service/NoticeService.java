package com.lawer.service;

import com.lawer.pojo.User;


import java.util.List;
import java.util.Map;

public interface NoticeService {
    //添加公告
    void addNotice(Map<String, Object> map, User user);
    //通过id查询公告信息
    Map<String, Object> selectNoticeById(String id);
    //更新公告
    void updateNotice(Map<String, Object> map);
    //删除公告
    void deleteNotice(String id);
    //添加用户公告
    void addUserNotice(Map<String, Object> map);
    //更新用户公告
    void updateUserNotice(Map<String, Object> map);
    //获取所有公告
    List<Map<String,Object>> getAllNotice(Map<String, Object> map);
    //获取所有公告数量
    int getAllNoticeCount(Map<String, Object> map);
    //添加提醒消息
    void addMsg(Map<String, Object> map);
    //更新提醒消息
    void updateMsg(Map<String, Object> map);
    //查询提醒消息
    Map<String,Object> selectMsg(Map<String, Object> map);
    //查看置顶数
    int selectTopCount(String busid);
}

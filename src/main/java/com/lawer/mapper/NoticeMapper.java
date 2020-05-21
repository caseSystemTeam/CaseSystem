package com.lawer.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 公告mapper
 */
public interface NoticeMapper {
    //添加公告
    void addNotice(@Param("map") Map<String, Object> map);
    //通过id查询公告信息
    Map<String, Object> selectNoticeById(String id);
    //更新公告
    void updateNotice(@Param("map") Map<String, Object> map);
    //删除公告
    void deleteNotice(String id);
    //添加用户公告
    void addUserNotice(@Param("map") Map<String, Object> map);
    //更新用户公告
    void updateUserNotice(@Param("map") Map<String, Object> map);
    //获取所有公告
    List<Map<String,Object>> getAllNotice(@Param("map") Map<String, Object> map);
    //获取所有公告数量
    int getAllNoticeCount(@Param("map") Map<String, Object> map);
    //添加提醒消息
    void addMsg(@Param("map") Map<String, Object> map);
    //更新提醒消息
    void updateMsg(@Param("map") Map<String, Object> map);
    //查询提醒消息
    Map<String,Object> selectMsg(@Param("map") Map<String, Object> map);
    //查看置顶数
    int selectTopCount(String busid);


}

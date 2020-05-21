package com.lawer.service.impl;

import com.lawer.common.DateUtil;
import com.lawer.mapper.NoticeMapper;
import com.lawer.pojo.User;
import com.lawer.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    public void addNotice(Map<String, Object> map, User user) {

        map.put("create_time", DateUtil.getToday());
        map.put("update_time",map.get("create_time"));
        map.put("isnew",0);
        map.put("scount",0);
        map.put("create_userid",user.getId());
        map.put("busid",user.getBusId());
       noticeMapper.addNotice(map);

    }

    @Override
    public Map<String, Object> selectNoticeById(String id) {
        return noticeMapper.selectNoticeById(id);
    }

    @Override
    public void updateNotice(Map<String, Object> map) {
        noticeMapper.updateNotice(map);
    }

    @Override
    public void deleteNotice(String id) {
        noticeMapper.deleteNotice(id);
    }



    @Override
    public void addUserNotice(Map<String, Object> map) {
         map.put("id",UUID.randomUUID().toString());
         map.put("isread",0);
        noticeMapper.addUserNotice(map);
    }

    @Override
    public void updateUserNotice(Map<String, Object> map) {
      noticeMapper.updateUserNotice(map);
    }

    @Override
    public List<Map<String, Object>> getAllNotice(Map<String, Object> map) {
        map.put("isTop",1);
        List<Map<String, Object>> list =noticeMapper.getAllNotice(map);
        if(list ==null){
            list = new ArrayList<>();
        }
        map.put("isTop",0);
        list.addAll(noticeMapper.getAllNotice(map));
        return list;
    }

    @Override
    public int getAllNoticeCount(Map<String, Object> map) {
        return noticeMapper.getAllNoticeCount(map);
    }

    @Override
    public void addMsg(Map<String, Object> map) {
        noticeMapper.addMsg(map);
    }

    @Override
    public void updateMsg(Map<String, Object> map) {
        noticeMapper.updateMsg(map);
    }

    @Override
    public Map<String, Object> selectMsg(Map<String, Object> map) {
        return noticeMapper.selectMsg(map);
    }

    @Override
    public int selectTopCount(String busid) {
        return noticeMapper.selectTopCount(busid);
    }
}

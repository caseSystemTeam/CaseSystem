package com.lawer.mapper;


import com.lawer.pojo.UserRole;
import com.lawer.util.MyMapper;





public interface UserRoleMapper extends MyMapper<UserRole> {
    /**
     * 更新用户角色
     */
    void updateUserRole(UserRole userRole);
}
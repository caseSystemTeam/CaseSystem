package com.lawer.service.impl;

import com.lawer.mapper.*;
import com.lawer.pojo.*;
import com.lawer.service.RoleService;

import com.lawer.util.ResponseVo;
import com.lawer.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Set<String> findRoleByUserId(String userId) {
        return roleMapper.findRoleByUserId(userId);
    }

    @Override
    public List<Role> selectRoles(Role role) {
        return roleMapper.selectRoles(role);
    }

    @Override
    public int insert(Role role) {
        role.setRoleId(UUID.randomUUID().toString());
        role.setStatus(1);
        role.setCreateTime(new Date());
        return roleMapper.insert(role);
    }

    @Override
    public int updateStatusBatch(List<String> roleIds, Integer status) {
        Map<String,Object> params = new HashMap<String,Object>(2);
        params.put("roleIds",roleIds);
        params.put("status",status);
        return roleMapper.updateStatusBatch(params);
    }

    @Override
    public Role findById(Integer id) {
        Role role = new Role();
        role.setId(id);
        return roleMapper.selectByPrimaryKey(role);
    }

    @Override
    public int updateByRoleId(Role role) {
        Map<String,Object> params  = new HashMap<>(3);
        params.put("name",role.getName());
        params.put("description",role.getDescription());
        params.put("role_id",role.getRoleId());
        return roleMapper.updateByRoleId(params);
    }

    @Override
    public List<Permission> findPermissionsByRoleId(String roleId) {
        return permissionMapper.findByRoleId(roleId);
    }

    @Override
    public ResponseVo addAssignPermission(String roleId, List<String> permissionIds) {
        try{
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermissionMapper.delete(rolePermission);
            for(String permissionId : permissionIds){
                rolePermission.setId(null);
                rolePermission.setPermissionId(permissionId);
                rolePermissionMapper.insert(rolePermission);
            }
            return ResultUtil.success("分配权限成功");
        }catch(Exception e){
            return ResultUtil.error("分配权限失败");
        }
    }

    @Override
    public List<User> findByRoleId(String roleId) {
        return userMapper.findByRoleId(roleId);
    }

    @Override
    public List<User> findByRoleIds(List<String> roleIds) {
        return userMapper.findByRoleIds(roleIds);
    }

    public void updateUserRole(UserRole userRole){
        userRoleMapper.updateUserRole(userRole);
    }

}

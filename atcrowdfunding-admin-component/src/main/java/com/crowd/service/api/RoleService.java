package com.crowd.service.api;

import com.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface RoleService {

    PageInfo<Role> getPageInfo(String key,Integer pageNum,Integer pageSize);

    void saveRole(Role role);

    void UpdateRole(Role role);

    void removeRoleById(List<Integer> id);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnAssignedRole(Integer adminId);

    List<String> getAssignedRoleNameListByLoginAcct(String loginAcct);
}

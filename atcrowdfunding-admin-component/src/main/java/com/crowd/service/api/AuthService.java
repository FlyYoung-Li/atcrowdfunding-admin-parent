package com.crowd.service.api;

import com.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-09-22 10:46
 **/
public interface AuthService {
    List<Auth> getAllAuth();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAssignAuthRelationship(Map<String, List<Integer>> map);

    List<String> getAssignedAuthNameListByLoginAcct(String loginAcct);
}

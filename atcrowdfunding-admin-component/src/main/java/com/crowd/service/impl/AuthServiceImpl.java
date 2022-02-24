package com.crowd.service.impl;

import com.crowd.entity.Auth;
import com.crowd.entity.AuthExample;
import com.crowd.mapper.AuthMapper;
import com.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-09-22 10:46
 **/
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAllAuth() {
        //这个就是查询所有的数据（selectByExample）
        List<Auth> auths = authMapper.selectByExample(new AuthExample());
        return auths;
    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        List<Integer> authIdList = authMapper.selectAssignedAuthIdByRoleId(roleId);
        return authIdList;
    }

    @Override
    public void saveRoleAssignAuthRelationship(Map<String, List<Integer>> map) {
        //取出roleId(只有一个)
        List<Integer> roleIdList = map.get("roleId");
        Integer roleId = roleIdList.get(0);
        //取出authIdList
        List<Integer> authIdList = map.get("authIdList");
        //保存分配结果之前,先删除之前的分配结果
        authMapper.deleteRoleAssignedAuthRelationship(roleId);
        //数组中没数据，就是[]，此时数组不为null
        if (authIdList != null && authIdList.size() > 0) {
            authMapper.saveRoleAssignAuthRelationship(roleId, authIdList);
        }
    }

    @Override
    public List<String> getAssignedAuthNameListByLoginAcct(String loginAcct) {
        List<String> authNameList = authMapper.selectAssignedAuthNameListByLoginAcct(loginAcct);
        return  authNameList;
    }
}

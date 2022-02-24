package com.crowd.service.impl;

import com.crowd.entity.Role;
import com.crowd.entity.RoleExample;
import com.crowd.mapper.RoleMapper;
import com.crowd.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    /**
     *  获取pageInfo<Role>
     * @param keyword 关键字
     * @param pageNum   pageNum
     * @param pageSize  pageSize
     * @return      PageInfo
     */

    public PageInfo<Role> getPageInfo(String keyword, Integer pageNum, Integer pageSize){
        //1.开启PageHelper的分页
        PageHelper.startPage(pageNum,pageSize);
        //2.得到List，封装PageInfo
        List<Role> roles = roleMapper.selectRoleByKeyword(keyword);
       return new PageInfo(roles);
    }

    /**
     *  保存role
     * @param role
     */
    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void UpdateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void removeRoleById(List<Integer> ids) {
        //复杂的操作，都可以通过roleExample来做，创建标准criteria
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(ids);
        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public List<Role> getAssignedRole(Integer adminId) {
        return  roleMapper.selectAssignedRole(adminId);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer adminId) {
        return  roleMapper.selectUnAssignedRole(adminId);
    }

    @Override
    public  List<String> getAssignedRoleNameListByLoginAcct(String loginAcct){
        List<String> roleNameList = roleMapper.selectAssignedRoleNameListByLoginAcct(loginAcct);
        return roleNameList;
    }
}

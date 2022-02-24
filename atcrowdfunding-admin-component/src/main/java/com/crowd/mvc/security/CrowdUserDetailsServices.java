package com.crowd.mvc.security;

import com.crowd.entity.Admin;
import com.crowd.entity.AdminExample;
import com.crowd.mapper.AdminMapper;
import com.crowd.mapper.AuthMapper;
import com.crowd.mapper.RoleMapper;
import com.crowd.service.api.AdminService;
import com.crowd.service.api.AuthService;
import com.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-09-29 00:27
 **/
@Component
public class CrowdUserDetailsServices implements UserDetailsService {

    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String loginAcct) throws UsernameNotFoundException {
        //根据表单传过来的loginAcct
        // 1.从数据库中查出Admin对象（用户），方法里面已经做了判断

        Admin  admin = adminService.getAdminByLoginAcct(loginAcct);

        // 2. 从数据库用户-角色关联表中查出该用户所对应的角色
        List<String> roleNameList = roleService.getAssignedRoleNameListByLoginAcct(loginAcct);
        // 3. 从数据库角色-权限关联表中查出该用户所对应的角色所有的权限
        List<String> authNameList = authService.getAssignedAuthNameListByLoginAcct(loginAcct);

        // 4. 给GrantedAuthority封装角色、权限信息
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
            // 封装角色
        for (String roleName : roleNameList) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + roleName);
            grantedAuthorityList.add(simpleGrantedAuthority);
        }
            //封装权限
        for (String authName : authNameList) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
            grantedAuthorityList.add(simpleGrantedAuthority);
        }
        //现在有一个问题，就是权限中，有可能是有null值的（在sql语句中已经判断）
        return new AdminUserDetails(admin.getLoginAcct(),admin.getUserPswd(),grantedAuthorityList,admin);
    }
}

package com.crowd.mvc.security;

import com.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-09-29 01:50
 **/
public class AdminUserDetails extends User {

    private static final long serialVersionUID = -2327665639177556901L;

    private Admin admin;

    public AdminUserDetails(String username,
                            String password,
                            Collection<? extends GrantedAuthority> authorities,
                            Admin admin) {
        super(username, password, authorities);
        this.admin = admin;
        //为了登录成功后，获取不到密码信息
        admin.setUserPswd(null);
    }

    public Admin getAdmin() {
        return admin;
    }

}

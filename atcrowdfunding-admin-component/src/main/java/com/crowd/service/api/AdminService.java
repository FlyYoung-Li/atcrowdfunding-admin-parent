package com.crowd.service.api;

import com.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李晓扬
 * @version 1.0
 * @description: TODO
 * @date 2021/8/24 22:05
 */

public interface AdminService {

    void saveAdmin(Admin admin);

    Admin getAdminByAcct(String acct, String pswd);

    PageInfo<Admin> getPageInfo(String keyword,Integer pageSize,Integer pageNum);

    void removeAdmin(Integer id);


    Admin getAdminById(Integer id);

    void updateAdmin(Admin admin);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);

    Admin getAdminByLoginAcct(String loginAcct);
}

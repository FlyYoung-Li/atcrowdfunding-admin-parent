package com.crowd.service.impl;

import com.crowd.constant.CrowdConstant;
import com.crowd.entity.Admin;
import com.crowd.entity.AdminExample;
import com.crowd.exception.LoginAcctAlreadyInUseException;
import com.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.crowd.exception.LoginFailedException;
import com.crowd.mapper.AdminMapper;
import com.crowd.service.api.AdminService;
import com.crowd.util.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author 李晓扬
 * @version 1.0
 * @description: TODO
 * @date 2021/8/24 22:06
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)

public class AdminServiceImpl implements AdminService {

    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void saveAdmin(Admin admin) {

        //密码明文加密，原来使用md5，现在使用Spring Security 的盐值加密 ，BCryptPasswordEncoder
       /* String userPswd = admin.getUserPswd();
        String userPswdCoded = CrowdUtil.md5(userPswd);
        admin.setUserPswd(userPswdCoded);*/

        String userPswd = admin.getUserPswd();
        String encode = bCryptPasswordEncoder.encode(userPswd);
        admin.setUserPswd(encode);

        //创建时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(date);
        admin.setCreateTime(createTime);

        try {
            int insert = adminMapper.insert(admin);
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("异常类型："+exception.getClass().getName());

            if(exception instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCOUNT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public Admin getAdminByAcct(String acct, String pswd) {
        //发过来的参数可能为空（应该在前端进行校验，这里自己多写一点，老师没写）
        //同时，在这里做了判断之后，后面md5（）方法里面的判断，就不会为空，不会返回到system-erro
        if ((acct == null || acct.length() == 0) || (pswd == null || pswd.length() == 0)) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_ACCT_OR_PSWD_IS_NULL);
        }
        // 1.根据登录账号查询Admin对象（只是账号）
        AdminExample adminExample = new AdminExample();

        AdminExample.Criteria criteria = adminExample.createCriteria();

        criteria.andLoginAcctEqualTo(acct);

        List<Admin> admins = adminMapper.selectByExample(adminExample);

        // 2.判断admin是否为空
        //先判断admins这个集合是否为空
        if (admins == null || admins.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (admins.size() > 1) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_SYSTEM_ERROR_ACCOUNT_NOT_UNIQUE);
        }
        // 3.如果Admin对象为null，抛出异常
        Admin admin = admins.get(0);
        if (admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 4.如果Admin对象不为null，则将数据库密码从Admin对象中取出
        String pswdDB = admin.getUserPswd();
        // 5.下面进行密码的比较，先对于表单提交的明文密码进行加密
        String pswdForm = CrowdUtil.md5(pswd);
        // 6.对表单发过来的密码与数据库中的密码校验
        if (!Objects.equals(pswdForm, pswdDB)) {
            // 7.如果不同，抛异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 8.如果一致返回Admin对象
        return admin;

    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword,Integer pageSize,Integer pageNum) {
        // 1.使用PageHelper的静态方法开启分页
        PageHelper.startPage(pageNum,pageSize);
        // 2.调用adminMapper返回封装好Admin的List对象
        List<Admin> admins = adminMapper.selectAdminByKeyword(keyword);
        // 3.查询好的结果封装到pageInfo
        PageInfo<Admin> adminPageInfo = new PageInfo<>(admins);
        return adminPageInfo;
    }

    @Override
    public void removeAdmin(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Admin getAdminById(Integer id) {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        return admin;
    }

    @Override
    public void updateAdmin(Admin admin) {
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("异常类型："+exception.getClass().getName());

            if(exception instanceof DuplicateKeyException){

                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCOUNT_ALREADY_IN_USE);
            }
        }
    }
    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        // 1.删除原来adminId旧的关联关系数据
        adminMapper.deleteAdminRoleRelationship(adminId);
        //mapper命名：就是insert、select、update、delete
        //handler（api）add/save、get、edit、remove
        // 2.保存新的关联关系
        if(roleIdList != null && roleIdList.size() >0){
             adminMapper.insertAdminRoleRelationship(adminId,roleIdList);
        }
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);

        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        //loginAcct，由唯一索引，查询返回只有一个
        if(adminList == null || adminList.size() == 0){
            throw new UsernameNotFoundException("您输入的账户并不存在");
        }
        Admin admin = adminList.get(0);

        return admin;
    }

}

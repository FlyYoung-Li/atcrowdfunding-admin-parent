package com.crowd.mvc.handler;

import com.crowd.constant.CrowdConstant;
import com.crowd.entity.Admin;
import com.crowd.service.api.AdminService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author 李晓扬
 * @version 1.0
 * @description: TODO
 * @date 2021/9/7 7:55
 */


@Controller
public class AdminHandler {
    private Logger logger = LoggerFactory.getLogger(AdminHandler.class);
    @Autowired
    private AdminService adminService;

   /* @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String acct,
                          @RequestParam("userPswd") String pswd,
                          HttpSession session) {

        //调用service方法，进行账号和密码的校验（service主要写逻辑），返回admin，说明登录成功;
        //如果账号、密码有问题，会自己抛异常
        Admin admin = adminService.getAdminByAcct(acct, pswd);

        //将登录成功返回的账户存入session中
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);

        return "redirect:/admin/to/main/page.html";
    }*/

    /**
     *  登录账户后的退出登录操作
     * @param session 当前session销毁（用户退出）
     * @return 返回登录页面
     */
    @RequestMapping("/admin/do/logout/page.html")
    public String doLogout(HttpSession session) {
        //session手动设置失效
        session.invalidate();
        logger.info("session手动设置失效成功");
        return "redirect:/admin/to/login/page.html";
    }

    /**
     *  用户维护的分页方法（带关键字查询和不带关键字查询）
     * @param keyword 关键字
     * @param pageNum 显示页码
     * @param pageSize 显示记录数
     * @param map 容器，将返回的pageInfo对象存入隐含模型，最终存入请求域中
     * @return
     */
    @RequestMapping("/admin/get.html")
    public String getPageInfo(@RequestParam(value = "keyword",defaultValue = "") String keyword,
                                  @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                                  Map map){
        // 1.调用adminService的方法得到PageInfo（封装好的list）
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword,pageSize,pageNum);

        // 2.加入隐含模型中
        map.put(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);
        return "admin-page";
    }

    /**
     *  移去admin用户
     * @param id 要移去admin的id
     * @param keyword 关键字
     * @param pageNum pageNum
     * @return 原来的页面
     */
    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String removeAdmin(@PathVariable("adminId") Integer id,
                                  @PathVariable(value = "keyword") String keyword,
                                  @PathVariable(value = "pageNum") Integer pageNum){
        adminService.removeAdmin(id);
        return "redirect:/admin/get.html?keyword="+keyword+"&pageNum="+pageNum;
    }

    /**
     *  新admin
     * @param loginAcct  账号
     * @param userName  userName
     * @param email    email
     * @param userPswd 密码
     * @return  返回最近添加记录的最后页面
     */
    @PreAuthorize("hasAuthority('user:save')")
    @RequestMapping("/admin/save.html")
    public String saveAdmin(@RequestParam("loginAcct")String loginAcct,
                               @RequestParam("userName")String userName,
                               @RequestParam("email")String email,
                               @RequestParam("userPswd") String userPswd){

        Admin admin = new Admin(null, loginAcct, userPswd, userName, email, null);
        adminService.saveAdmin(admin);
        return "redirect:/admin/get.html?pageNum="+ Integer.MAX_VALUE;
    }

    /**
     *  去编辑用户页面（带数据）
     * @param id 要编辑用户的id
     * @param model 编辑用户的信息
     * @return 编辑页面
     */
    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(@RequestParam("id")Integer id,Model model){
        Admin admin = adminService.getAdminById(id);
        model.addAttribute(CrowdConstant.ATTR_NAME_UPDATE_ADMIN,admin);
        return "admin-edit";
    }

    /**
     *  编辑用户后更新用户的操作
     * @param admin 接收更新后的用户
     * @param keyword 关键字
     * @param pageNum 当前页
     * @return 重定向原来的页面
     */
    @RequestMapping("/admin/update.html")
    public String updateAdmin(Admin admin,
                              @RequestParam("keyword") String keyword,
                              @RequestParam("pageNum") Integer pageNum){
        adminService.updateAdmin(admin);
        return  "redirect:/admin/get.html?keyword="+keyword+"&pageNum="+pageNum;
    }
}

package com.crowd.mvc.handler;

import com.crowd.entity.Auth;
import com.crowd.entity.Role;
import com.crowd.service.api.AdminService;
import com.crowd.service.api.AuthService;
import com.crowd.service.api.RoleService;
import com.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-09-20 17:01
 **/
@Controller
public class AssignHandler {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;
    /**
     *  去用户分配角色的页面
     * @return
     */
    @RequestMapping("/assign/to/assign/page.html")
    public String toAssignRolePage(@RequestParam("id")Integer adminId, Model model){
        // 1.查询已分配的角色(role)
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        // 2.查询未分配的角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);
        // 3.存入模型（本质是其实是：request.setAttribute("attributeName",attributeVal)）
        model.addAttribute("assignedRole",assignedRoleList);
        model.addAttribute("unAssignedRole",unAssignedRoleList);
        return "assign-role";

    }

    /**
     *                      点击保存，将用户分配的角色保存到中间表中
     * @param adminId       当前修改的用户id(就是Admin)
     * @param keyword       如果带关键词搜索，带上（方便分页显示还是原来的页面）
     * @param pageNum       当前搜索的页码（方便分页显示还是原来的页面）
     * @param roleIdList    当前用户id所属的角色id的list
     * @return
     */
    @RequestMapping("/assign/do/role/assign.html")
    public String assignRole(@RequestParam("id")Integer adminId,
                             @RequestParam("keyword")String keyword,
                             @RequestParam("pageNum")Integer pageNum,
                             @RequestParam(value = "roleIdList",required = false)List<Integer> roleIdList){
        //只能调用adminService和roleService(没有assignMapper，有assignService也调用不了)
        //给用户分配角色，查询的时候使用roleService;现在设置，使用adminService
       adminService.saveAdminRoleRelationship(adminId,roleIdList);
        return "redirect:/admin/get.html?keyword="+keyword+"&pageNum="+pageNum;
    }

    /**
     *  点击角色分配权限按钮，先显示树形结构
     * @return authList
     */
    @ResponseBody
    @RequestMapping("/assign/get/all/auth.json")
    public ResultEntity getAllAuth(){

        List<Auth> allAuth = authService.getAllAuth();

        return ResultEntity.successWithData(allAuth);
    }

    /**
     *  点击角色分配权限按钮，显示完树形结构后，在树形结构上选中该角色的权限（获取该角色的权限）
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity getAssignedAuthIdByRoleId(@RequestParam("roleId") Integer roleId){

        List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);

        return ResultEntity.successWithData(authIdList);
    }

    /**
     * 执行分配
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/assign/do/role/assign/auth.json")
    public ResultEntity saveRoleAssignAuthRelationship(@RequestBody Map<String,List<Integer>> map){

        authService.saveRoleAssignAuthRelationship(map);

        return ResultEntity.successWithoutData();
    }

}

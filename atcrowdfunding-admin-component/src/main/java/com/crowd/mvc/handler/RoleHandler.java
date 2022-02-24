package com.crowd.mvc.handler;

import com.crowd.entity.Role;
import com.crowd.service.api.RoleService;
import com.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class RoleHandler {
    @Autowired
    private RoleService roleService;

    /**
     *  得到分页所需的PageInfo
     * @param keyword 关键字
     * @param pageNum 当前页
     * @param pageSize 当前页显示个数
     * @return  ResultEntity对象
     */
    @PreAuthorize("hasRole('部长')")
    @ResponseBody
    @RequestMapping("/role/get/page/info.json")
    public ResultEntity getPageInfo(@RequestParam(value = "keyword",defaultValue = "") String keyword,
                                    @RequestParam(value = "pageNum",defaultValue = "1")  Integer pageNum ,
                                    @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){

        PageInfo<Role> pageInfo = roleService.getPageInfo(keyword, pageNum, pageSize);

        return ResultEntity.successWithData(pageInfo);
    }

    /**
     *  添加role
     * @param role
     * @return 成功的没携带数据的ResultEntity
     */
    @ResponseBody
    @RequestMapping("/role/add.json")
    public ResultEntity saveRole(Role role){
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }

    /**
     *  修改角色
     * @param role 传过来的参数，封装好Role
     * @return 成功的没携带数据的ResultEntity
     */
    @ResponseBody
    @RequestMapping("/role/update.json")
    public ResultEntity upadeRole(Role role){
        roleService.UpdateRole(role);
        return ResultEntity.successWithoutData();
    }

    /**
     *  根据发过来的id集合，移除（批量移除和单个移除都在一起）
     * @param id
     * @return 成功的没携带数据的ResultEntity
     */
    @ResponseBody
    @RequestMapping("/role/remove.json")
    public ResultEntity removeRoleById(@RequestBody List<Integer> id){
        roleService.removeRoleById(id);
        return ResultEntity.successWithoutData();
    }

}

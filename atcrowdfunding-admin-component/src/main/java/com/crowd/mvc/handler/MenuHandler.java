package com.crowd.mvc.handler;

import com.crowd.entity.Menu;
import com.crowd.service.api.MenuService;
import com.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-09-16 00:14
 **/
@Controller
public class MenuHandler {

    @Autowired
    private MenuService menuService;
    /**
     * 现在，MyBatis逆向工程生成的Menu对象，和数据库中的信息是一样的，但是还少了表示父子关系的List<Menu>
     * 集合
     */
   /* @ResponseBody
    @RequestMapping("/menu/get/whole/tree.json")
    public ResultEntity getWholeTree(){
        //得到未显示父子关系Menu对象集合
        List<Menu> menuList = menuService.getMenuList();

        for (Menu menu : menuList) {
            if(menu.getPid()!=null){
                for (Menu menu1 : menuList) {
                    if(menu.getPid() == menu1.getId()){
                      menu1.getChildren().add(menu);
                    }
                }
            }
        }
        return ResultEntity.successWithData(menuList);
    } */

    /**
     *  menu最为实体类，返回树形结构（返回根节点）
     * @return
     */
    @ResponseBody
    @RequestMapping("/menu/get/whole/tree.json")
    public ResultEntity getWholeTree(){
        //得到未显示父子关系Menu对象集合
        List<Menu> menuList = menuService.getMenuList();
        //根节点
        Menu root = null;
        //创建对应关系
        HashMap<Integer, Menu> map = new HashMap<>();
        for (Menu menu : menuList) {
            Integer id = menu.getId();
            map.put(id,menu);
        }
        for (Menu menu : menuList) {
            Integer pid = menu.getPid();
            //pid不为null，说明有父节点
            if(pid != null){
                Menu father = map.get(pid);
                father.getChildren().add(menu);
                continue;
            }if(pid == null){
                root = menu;
            }
        }
        return ResultEntity.successWithData(root);
    }
    //点击新增模态框的保存按钮
    @ResponseBody
    @RequestMapping("/menu/add.json")
    public ResultEntity addMenu(Menu menu){

        menuService.addMenu(menu);
        return ResultEntity.successWithoutData();
    }
    //点击删除模态框的删除按钮
    @ResponseBody
    @RequestMapping("/menu/remove.json")
    public ResultEntity removeMenu(@RequestParam("id") Integer id){

        menuService.removeMenu(id);
        return ResultEntity.successWithoutData();
    }
    //点击更新模态框的更新按钮
    @ResponseBody
    @RequestMapping("/menu/edit.json")
    public ResultEntity editMenu(Menu menu){

        menuService.editMenu(menu);
        return ResultEntity.successWithoutData();
    }


}

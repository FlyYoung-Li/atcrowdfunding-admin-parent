package com.crowd.mvc.handler;

import com.crowd.entity.Admin;
import com.crowd.entitydemo.ParamData;
import com.crowd.entitydemo.Student;
import com.crowd.service.api.AdminService;
import com.crowd.util.CrowdUtil;
import com.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 李晓扬
 * @version 1.0
 * @description: TODO 这是用于测试使用的，真实的Handler不是这个
 * @date 2021/8/28 10:57
 */
@Controller
public class FirstHandler {
    private Logger logger=LoggerFactory.getLogger(FirstHandler.class);
    @Autowired
    private AdminService adminService;
    @RequestMapping("/insert.html")
    public String test01(){
        adminService.saveAdmin(new Admin(null,"456","456","456","456","456"));
        System.out.println("看看是否执行成功");
        return "success";
    }
    //使用Ajax发送数组的三种方式：
    //使用ajax请求，就会有回调函数，返回success页面的信息（没有使用@ResponseBody）
    @ResponseBody
    //如果使用了ResponseBody，就是直接将返回的内容，返回到页面。
    @RequestMapping("/ajaxTest.html")
    public String ajaxTest01(@RequestParam("hello[]") Integer[] list){
        System.out.println(list);
        for (Integer integer : list) {
            System.out.println(integer);

        }
        return "success";
    }
    @ResponseBody
    @RequestMapping("/ajaxTest2.html")
    public String ajaxTest02(ParamData paramData){
        List<Integer> list = paramData.getList();
        System.out.println(list);
        for (Integer integer : list) {
            System.out.println(integer);
        }
        return "success";
    }
    @ResponseBody
    @RequestMapping("/ajaxTest3.html")
    public String ajaxTest03(@RequestBody List<Integer> list){
        System.out.println(list);
        for (Integer integer : list) {
            System.out.println(integer);
        }
        return "success";
    }
    @ResponseBody
    //复杂对象的封装
    @RequestMapping("/ajaxTest4.json")
    public ResultEntity<Student> ajaxTest04(@RequestBody Student student, HttpServletRequest request){
        boolean b = CrowdUtil.judgeRequestType(request);
        logger.info("是不是ajax请求："+b);
        System.out.println(student);
        System.out.println("******************");
        logger.info(student.toString());
        System.out.println("************");
        ResultEntity<Student> studentResultEntity = ResultEntity.successWithData(student);
        System.out.println(studentResultEntity);
        int i = 10/0;

        /*logger.info("*******************测试注解配置的异常映射机制*****************");
        String a =null;
        System.out.println(a.length());*/
        return studentResultEntity;
    }
    @RequestMapping("/admin/test.html")
    public String test(HttpServletRequest request){
        boolean b = CrowdUtil.judgeRequestType(request);
        logger.info("是不是ajax请求："+b);
        System.out.println("现在通过注解的方式，配置错误，然后测试一下普通异常映射机制");
//        int i = 10/0;
        logger.info("*******************测试异常映射机制处理空指针异常*****************");
        String a =null;
        System.out.println(a.length());
        return "success";
    }

}

package com.crowdfunding.handler;

import com.crowd.constant.CrowdConstant;
import com.crowd.util.ResultEntity;
import com.crowdfunding.crowd.entity.vo.AddressVO;
import com.crowdfunding.crowd.entity.vo.MemberLoginVO;
import com.crowdfunding.crowd.entity.vo.OrderProjectVO;
import com.crowdfunding.crowd.entity.vo.OrderVO;
import com.crowdfunding.crowd.service.api.MysqlRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-22 18:24
 **/
@Controller
public class OrderHandler {
    @Autowired
    private MysqlRemoteService mysqlRemoteService;
    //订单的第一个阶段 （确认回报信息）
    @RequestMapping("/to/confirm/return/page/{returnId}/{projectId}")
    public String toConfirmReturnPage(
            @PathVariable("returnId") Integer returnId,
            @PathVariable("projectId") Integer projectId,
            HttpSession session){

        // 调用微服务远程接口，获取resultEntity
        ResultEntity<OrderProjectVO> orderProjectVOResultEntity= mysqlRemoteService.getOrderProjectVO(returnId,projectId);
        // 判断结果
        String result = orderProjectVOResultEntity.getResult();
        // 返回成功
        if(ResultEntity.SUCCESS.equals(result)){
            OrderProjectVO orderProjectVO = orderProjectVOResultEntity.getData();
            session.setAttribute(CrowdConstant.ATTR_NAME_ORDER_PROJECTVO,orderProjectVO);
        }
        // 返回失败直接在页面通过thymeleaf进行判断
        return "order-confirm-return";
    }
    //订单的第二个阶段（确认订单信息）
    @RequestMapping("/confirm/order/{returnCount}")
    public String toConfirmOrderPage(
            @PathVariable("returnCount") Integer returnCount,
            HttpSession session){
        // 1.session中取出orderProjectVO
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_ORDER_PROJECTVO);
        // 2.设置用户输入回报数量（购买数）
        orderProjectVO.setReturnCount(returnCount);
        // 3.再次存进redis中，覆盖session库
        session.setAttribute(CrowdConstant.ATTR_NAME_ORDER_PROJECTVO,orderProjectVO);

        // 第二阶段页面所需的AddressVOList,根据用户id，查找
        MemberLoginVO loginMember = (MemberLoginVO)session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = loginMember.getId();
        ResultEntity<List<AddressVO>> addressVOResultEntity = mysqlRemoteService.getAddressVOList(memberId);
        // 获取成功，存入session域中
        String result = addressVOResultEntity.getResult();
        if(ResultEntity.SUCCESS.equals(result)){
            List<AddressVO> addressVOList = addressVOResultEntity.getData();

            session.setAttribute(CrowdConstant.ATTR_NAME_ADDRESSVOLIST,addressVOList);
        }
       // 获取失败，thymeleaf进行判断
        return "order-confirm-order";
    }
    @ResponseBody
    @RequestMapping("/add/receiver/address")
    public ResultEntity saveReceiverAddress(AddressVO addressVO){
        System.out.println(addressVO);

           ResultEntity saveAddressResult =  mysqlRemoteService.saveReceiverAddress(addressVO);

        return saveAddressResult;
    }





}

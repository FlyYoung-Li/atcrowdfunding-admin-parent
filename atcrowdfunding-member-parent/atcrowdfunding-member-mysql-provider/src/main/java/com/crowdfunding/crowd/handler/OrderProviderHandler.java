package com.crowdfunding.crowd.handler;

import com.crowd.util.ResultEntity;
import com.crowdfunding.crowd.entity.vo.AddressVO;
import com.crowdfunding.crowd.entity.vo.OrderProjectVO;
import com.crowdfunding.crowd.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-22 19:45
 **/
@RestController
public class OrderProviderHandler {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/get/OrderProjectVO/remote")
    public ResultEntity<OrderProjectVO> getOrderProjectVO(
            @RequestParam("returnId") Integer returnId,
            @RequestParam("projectId")Integer projectId)
    {
        // handler中主要就是负责返回ResultEntity，获取数据是在service中做的
        // try-catch，有异常或者没异常，只有两种情况，都返回ResultEntity
        try {
            OrderProjectVO orderProjectVO = orderService.getOrderProjectVO(returnId,projectId);

            return ResultEntity.successWithData(orderProjectVO);

        } catch (Exception exception) {

            exception.printStackTrace();

            return ResultEntity.failed(exception.getMessage());
        }
    }

    @RequestMapping("/get/addressVOList/remote")
    ResultEntity<List<AddressVO>> getAddressVOList(@RequestParam("memberId") Integer memberId){
        try {
            List<AddressVO> addressVOList = orderService.getAddressVOList(memberId);
            return ResultEntity.successWithData(addressVOList);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }
    }

    @RequestMapping("/save/receiverAddress/remote")
    ResultEntity saveReceiverAddress(@RequestBody AddressVO addressVO){
        try {

            orderService.saveReceiverAddress(addressVO);
            return ResultEntity.successWithoutData();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }
    }
}

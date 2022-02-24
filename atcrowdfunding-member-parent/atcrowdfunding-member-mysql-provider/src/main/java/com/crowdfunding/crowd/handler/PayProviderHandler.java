package com.crowdfunding.crowd.handler;

import com.crowd.util.ResultEntity;
import com.crowdfunding.crowd.entity.vo.OrderVO;
import com.crowdfunding.crowd.service.api.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-26 14:57
 **/
@RestController
public class PayProviderHandler {
    @Autowired
    private PayService payService;
    @RequestMapping("save/orderVO/remote")
    ResultEntity saveOrderVO(@RequestBody OrderVO orderVO){
        try {
            payService.saveOrderPOAndOrderProject(orderVO);

            return ResultEntity.successWithoutData();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }

    }
}

package com.crowdfunding.crowd.service.impl;

import com.crowdfunding.crowd.entity.po.OrderPO;
import com.crowdfunding.crowd.entity.po.OrderProjectPO;
import com.crowdfunding.crowd.entity.vo.OrderProjectVO;
import com.crowdfunding.crowd.entity.vo.OrderVO;
import com.crowdfunding.crowd.mapper.OrderPOMapper;
import com.crowdfunding.crowd.mapper.OrderProjectPOMapper;
import com.crowdfunding.crowd.service.api.PayService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-26 14:58
 **/
@Service
@Transactional(readOnly = true)
public class PayServiceImpl implements PayService {

    @Autowired
    private OrderPOMapper orderPOMapper;
    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    @Override
    public void saveOrderPOAndOrderProject(OrderVO orderVO) {
        // 保存OrderPO（也就是存在t_order表）
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderVO,orderPO);
        orderPOMapper.insert(orderPO);
        // 获取自增主键，设置给orderProjectVO
        Integer orderId = orderPO.getId();
        OrderProjectVO orderProjectVO = orderVO.getOrderProjectVO();
        orderProjectVO.setOrderId(orderId);
        // 保存OrderProjectPO
        OrderProjectPO orderProjectPO = new OrderProjectPO();
        BeanUtils.copyProperties(orderProjectVO,orderProjectPO);
        orderProjectPOMapper.insert(orderProjectPO);

    }
}

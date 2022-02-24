package com.crowdfunding.crowd.service.api;

import com.crowdfunding.crowd.entity.po.AddressPO;
import com.crowdfunding.crowd.entity.vo.AddressVO;
import com.crowdfunding.crowd.entity.vo.OrderProjectVO;

import java.util.List;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-22 20:01
 **/
public interface OrderService {
    OrderProjectVO getOrderProjectVO(Integer returnId,Integer projectId);

    List<AddressVO> getAddressVOList(Integer memberId);

    void saveReceiverAddress(AddressVO addressVO);
}

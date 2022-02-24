package com.crowdfunding.crowd.service.impl;

import com.crowdfunding.crowd.entity.po.AddressPO;
import com.crowdfunding.crowd.entity.po.AddressPOExample;
import com.crowdfunding.crowd.entity.po.OrderProjectPO;
import com.crowdfunding.crowd.entity.vo.AddressVO;
import com.crowdfunding.crowd.entity.vo.OrderProjectVO;
import com.crowdfunding.crowd.mapper.AddressPOMapper;
import com.crowdfunding.crowd.mapper.OrderProjectPOMapper;
import com.crowdfunding.crowd.service.api.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-22 20:02
 **/
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

//    不应该注入这个mapper
//    @Autowired
//    private ReturnPOMapper returnPOMapper;
    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;
    @Autowired
    private AddressPOMapper addressPOMapper;
    @Override
    public OrderProjectVO getOrderProjectVO(Integer returnId,Integer projectId) {
        //service层主要负责逻辑，查询出这个对象。
        //调用mapper，要么使用逆向工程生成的方法；要么自己写mapper接口方法。
        OrderProjectVO orderProjectVO = orderProjectPOMapper.selectOrderProjectVO(returnId,projectId);



        return orderProjectVO;
    }

    @Override
    public List<AddressVO> getAddressVOList(Integer memberId) {
        // 1.new addressPOExample
        AddressPOExample addressPOExample = new AddressPOExample();
        // 2.创建criteria标准
        AddressPOExample.Criteria criteria = addressPOExample.createCriteria();
        criteria.andMemberIdEqualTo(memberId);
        // 3.查询出addressPOList
        List<AddressPO> addressPOList = addressPOMapper.selectByExample(addressPOExample);
        // 4.使用BeanUtils属性转换
        List<AddressVO> addressVOList = new ArrayList<>();

        for (AddressPO addressPO : addressPOList) {

            AddressVO addressVO = new AddressVO();

            BeanUtils.copyProperties(addressPO,addressVO);

            addressVOList.add(addressVO);
        }
        return addressVOList;
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public void saveReceiverAddress(AddressVO addressVO) {
        AddressPO addressPO = new AddressPO();

        BeanUtils.copyProperties(addressVO,addressPO);

        addressPOMapper.insert(addressPO);
    }
}

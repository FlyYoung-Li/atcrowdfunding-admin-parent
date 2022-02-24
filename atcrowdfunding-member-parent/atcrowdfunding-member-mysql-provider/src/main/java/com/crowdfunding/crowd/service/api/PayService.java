package com.crowdfunding.crowd.service.api;

import com.crowdfunding.crowd.entity.vo.OrderVO;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-26 14:58
 **/
public interface PayService {
    void saveOrderPOAndOrderProject(OrderVO orderVO);
}

package com.crowdfunding.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-22 19:35
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO  implements Serializable {

    private static final long serialVersionUID = 7679716120367503476L;
    private Integer id;
    // 订单号
    private String orderNumber;
    // 支付宝流水单号
    private String payOrderNumber;
    // 订单金额
    private Double orderAmount;
    // 是否开发票
    private Integer invoice;
    // 发票抬头
    private String invoiceTitle;
    // 备注
    private String orderRemark;

    private String addressId;;

    private OrderProjectVO orderProjectVO;

}

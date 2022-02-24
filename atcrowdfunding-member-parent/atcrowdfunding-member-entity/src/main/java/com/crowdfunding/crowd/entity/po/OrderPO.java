package com.crowdfunding.crowd.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPO {
    private Integer id;

    private String orderNumber;

    private String payOrderNumber;

    private Double orderAmount;

    private Integer invoice;

    private String invoiceTitle;

    private String orderRemark;

    private String addressId;


}
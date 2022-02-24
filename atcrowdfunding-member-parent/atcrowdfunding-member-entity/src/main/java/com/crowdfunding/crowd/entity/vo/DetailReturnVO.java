package com.crowdfunding.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-13 12:52
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailReturnVO {

    // 回报信息主键
    private Integer returnId;
    // 当前档位需要支持的金额
    private Integer supportMoney;
    // 单笔限购，为0时无限额，为正数时为具体的限额数
    private Integer signalPurchase;
    // 该档位支持者数量
    private Integer supportCount;
    // 运费，取值为0时，就是包邮
    private Integer freight;
    // 众筹成功后多少天发货
    private Integer returnDate;
    // 回报内容
    private String content;
}

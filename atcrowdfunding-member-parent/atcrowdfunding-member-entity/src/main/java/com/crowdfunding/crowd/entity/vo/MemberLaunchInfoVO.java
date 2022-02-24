package com.crowdfunding.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-06 10:19
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLaunchInfoVO implements Serializable {


    private static final long serialVersionUID = 110276218184210220L;

    // 简单介绍
    private String descriptionSimple;
    // 详细介绍
    private String descriptionDetail;
    // 联系电话
    private String phoneNum;
    // 客服电话
    private String serviceNum;
}

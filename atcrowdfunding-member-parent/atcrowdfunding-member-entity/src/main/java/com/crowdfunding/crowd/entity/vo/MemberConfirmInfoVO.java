package com.crowdfunding.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-06 10:25
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberConfirmInfoVO implements Serializable {

    private static final long serialVersionUID = 2478278139386134148L;

    // 易付宝账号
    private String paynum;
    // 法人身份证号
    private String cardnum;
}

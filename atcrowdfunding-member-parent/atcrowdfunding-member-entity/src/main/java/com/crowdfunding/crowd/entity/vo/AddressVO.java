package com.crowdfunding.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-22 19:34
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressVO implements Serializable {

    private static final long serialVersionUID = 5510391681505295065L;
    private Integer id;

    private String receiver;

    private String phoneNum;

    private String address;

    private Integer memberId;
}

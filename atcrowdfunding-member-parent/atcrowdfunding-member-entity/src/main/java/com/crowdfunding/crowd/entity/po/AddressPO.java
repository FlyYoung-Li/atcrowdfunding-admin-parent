package com.crowdfunding.crowd.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressPO {
    private Integer id;

    private String receiver;

    private String phoneNum;

    private String address;

    private Integer memberId;


}
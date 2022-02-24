package com.crowdfunding.crowd.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLaunchInfoPO {
    private Integer id;

    private Integer memberid;

    private String descriptionSimple;

    private String descriptionDetail;

    private String phoneNum;

    private String serviceNum;

}
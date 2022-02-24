package com.crowdfunding.crowd.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberConfirmInfoPO {
    private Integer id;

    private Integer memberid;

    private String paynum;

    private String cardnum;
}

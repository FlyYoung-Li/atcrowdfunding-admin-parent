package com.crowdfunding.crowd.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnPO {
    private Integer id;

    private Integer projectid;

    private Integer type;

    private Integer supportmoney;

    private String content;

    private Integer countint;

    private Integer signalpurchase;

    private Integer purchase;

    private Integer freight;

    private Integer invoice;

    private Integer returndate;

    private String describPicPath;


}
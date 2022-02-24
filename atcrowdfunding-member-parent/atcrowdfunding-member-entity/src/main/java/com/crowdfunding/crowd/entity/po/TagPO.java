package com.crowdfunding.crowd.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagPO {
    private Integer id;

    private Integer pid;

    private String name;

}
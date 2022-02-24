package com.crowdfunding.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-11 16:41
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortalTypeVO {

    private Integer id;

    private String name;

    private String remark;

    private List<PortalProjectVO> portalProjectVOList;
}

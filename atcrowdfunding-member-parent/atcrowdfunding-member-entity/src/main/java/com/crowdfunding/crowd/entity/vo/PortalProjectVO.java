package com.crowdfunding.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-11 18:14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortalProjectVO {
    private Integer projectId;

    private String projectName;

    private String headerPicturePath;

    private Integer money;

    private String deployDate;

    private Integer percentage;

    private Integer supporter;

    private String lastDate;
}

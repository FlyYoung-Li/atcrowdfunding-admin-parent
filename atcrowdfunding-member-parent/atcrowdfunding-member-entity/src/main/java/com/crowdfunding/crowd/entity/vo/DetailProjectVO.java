package com.crowdfunding.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-13 13:36
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailProjectVO {
    private Integer projectId;
    private String projectName;
    private String projectDesc;
    // 补一个属性
    private Integer status;
    private String statusText;
    // 补一个属性(筹集天数)
    private Integer day;
    // 应该是支持人数（受欢迎人数）
    private Integer followerCount;
    private Integer money;
    private Integer supportMoney;
    private Integer percentage;
    private String deployDate;
    private Integer lastDay;
    private Integer supportCount;
    private String headPicturePath;
    private List<String> detailPicturePathList;
    private List<DetailReturnVO> detailReturnVOList;
}

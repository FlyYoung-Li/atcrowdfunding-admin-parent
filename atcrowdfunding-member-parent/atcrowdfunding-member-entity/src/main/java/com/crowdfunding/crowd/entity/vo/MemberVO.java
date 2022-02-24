package com.crowdfunding.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-25 15:08
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {

    private String loginacct;

    private String userpswd;

    private String username;

    private String email;

    private String code;

    private String phoneNumber;
}

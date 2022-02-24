package com.crowdfunding.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-28 00:39
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginVO implements Serializable {

    private static final long serialVersionUID = 698555116180233198L;

    private Integer id;
    private String username;
    private String email;
}

package com.crowdfunding.crowd.entity.testentity;

import lombok.*;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-20 12:25
 **/
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class MemberTestPO {
    private String name;
    @NonNull
    private String key;
    @NonNull
    private Integer money;
}

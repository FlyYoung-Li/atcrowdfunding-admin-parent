package com.crowdfunding.crowd.service.api;

import com.crowd.util.ResultEntity;
import com.crowdfunding.crowd.entity.po.MemberPO;
import com.crowdfunding.crowd.entity.vo.MemberVO;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-21 21:56
 **/
public interface MemberService {
    public MemberPO getMemberPOByLoginAcct(String loginacct);

    public void addMemberPO(MemberPO memberPO);
}

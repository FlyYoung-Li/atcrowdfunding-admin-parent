package com.crowdfunding.crowd.service.impl;

import com.crowd.constant.CrowdConstant;
import com.crowd.util.ResultEntity;
import com.crowdfunding.crowd.entity.po.MemberPO;
import com.crowdfunding.crowd.entity.po.MemberPOExample;
import com.crowdfunding.crowd.entity.vo.MemberVO;
import com.crowdfunding.crowd.mapper.MemberPOMapper;
import com.crowdfunding.crowd.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-21 21:57
 **/
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    @Autowired(required = false)
    private MemberPOMapper memberPOMapper;
    @Override
    public MemberPO getMemberPOByLoginAcct(String loginacct) {
        // 1.创建Example对象
        MemberPOExample memberPOExample = new MemberPOExample();
        // 2.创建Criteria对象
        MemberPOExample.Criteria criteria = memberPOExample.createCriteria();
        // 3.封装查询条件
        criteria.andLoginacctEqualTo(loginacct);
        // 4.取出对象
        List<MemberPO> memberPOS = memberPOMapper.selectByExample(memberPOExample);
        // 5.判空（service层，报异常，外面无论正误都返回ResultEntity）
        if(memberPOS == null || memberPOS.size() == 0){
            throw new RuntimeException(CrowdConstant.MESSAGE_ERROR_ACCOUNT);
        }
        MemberPO memberPO = memberPOS.get(0);
        return memberPO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class,readOnly = false)
    public void addMemberPO(MemberPO memberPO) {
         memberPOMapper.insertSelective(memberPO);

    }
}

package com.crowdfunding.crowd.handler;

import com.crowd.util.ResultEntity;
import com.crowdfunding.crowd.entity.po.MemberPO;
import com.crowdfunding.crowd.entity.vo.MemberVO;
import com.crowdfunding.crowd.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-21 20:58
 **/
@RestController
public class MemberProviderHandler {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/get/memberPO/by/loginacct/remote")
    public ResultEntity<MemberPO> getMemberPO(@RequestParam("loginacct") String loginacct) {

        try {
            // 1.调用本地service，完成查询
            MemberPO memberPOByLoginAcct = memberService.getMemberPOByLoginAcct(loginacct);
            // 2.没有抛异常，就返回正常的ResultEntity
            return ResultEntity.successWithData(memberPOByLoginAcct);
        } catch (Exception exception) {
            // 3.如果抛出异常，捕获异常后，返回带错误信息的ResultEntity（总之都要返回ResultEntity）
            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }
    }

    @RequestMapping("/add/memberPO/remote")
    public ResultEntity<String> addMemberPO( @RequestBody MemberPO memberPO) {
        try {
            memberService.addMemberPO(memberPO);
            return ResultEntity.successWithoutData();

        } catch (Exception exception) {
            exception.printStackTrace();
            if (exception instanceof DuplicateKeyException) {
                return ResultEntity.failed("该用户名已经被注册");
            }
            return ResultEntity.failed(exception.getMessage());
        }
    }
}

package com.crowdfunding.crowd.service.api;

import com.crowd.util.ResultEntity;
import com.crowdfunding.crowd.entity.po.MemberPO;

import com.crowdfunding.crowd.entity.po.ProjectPO;
import com.crowdfunding.crowd.entity.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-21 20:54
 **/
@FeignClient("crowdfunding-mysql-provider")
public interface MysqlRemoteService {

    @RequestMapping("/get/memberPO/by/loginacct/remote")
    public ResultEntity<MemberPO> getMemberPO(@RequestParam("loginacct")String loginacct);
    @RequestMapping("/add/memberPO/remote")
    public ResultEntity<String> addMemberPO( @RequestBody MemberPO memberPO);
    @RequestMapping("/save/projectPO/remote")
    public ResultEntity<String> saveProjectPORemote(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId);
    @RequestMapping("/get/portalTypeVOList/remote")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeVOList();
    @RequestMapping("/get/projectPO/remote")
    public ResultEntity<ProjectPO> getProjectPOById(@RequestParam("id") Integer id);
    @RequestMapping("/get/detailProjectVO/remote")
    public ResultEntity<DetailProjectVO> getDetailProjectVOById(@RequestParam("id")Integer id);
    @RequestMapping("/get/OrderProjectVO/remote")
    public ResultEntity<OrderProjectVO> getOrderProjectVO(@RequestParam("returnId") Integer returnId,@RequestParam("projectId")Integer projectId);
    @RequestMapping("/get/addressVOList/remote")
    ResultEntity<List<AddressVO>> getAddressVOList(@RequestParam("memberId") Integer memberId);
    @RequestMapping("/save/receiverAddress/remote")
    ResultEntity saveReceiverAddress(@RequestBody AddressVO addressVO);
    @RequestMapping("save/orderVO/remote")
    ResultEntity saveOrderVO(@RequestBody OrderVO orderVO);
}

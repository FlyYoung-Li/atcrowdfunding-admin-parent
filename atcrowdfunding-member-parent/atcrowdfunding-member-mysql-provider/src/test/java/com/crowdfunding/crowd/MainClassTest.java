package com.crowdfunding.crowd;


import com.crowdfunding.crowd.entity.po.MemberPO;
import com.crowdfunding.crowd.entity.po.OrderProjectPO;
import com.crowdfunding.crowd.entity.po.ProjectPO;
import com.crowdfunding.crowd.entity.vo.DetailProjectVO;
import com.crowdfunding.crowd.entity.vo.OrderProjectVO;
import com.crowdfunding.crowd.entity.vo.PortalTypeVO;
import com.crowdfunding.crowd.mapper.*;
import com.crowdfunding.crowd.service.api.OrderService;
import com.crowdfunding.crowd.service.api.ProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-21 14:36
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class MainClassTest {
    @Autowired
    private ProjectPOMapper projectPOMapper;

    @Test
    public void selectPortalTypeVOList() {
        List<PortalTypeVO> portalTypeVOS = projectPOMapper.selectPortalTypeVOList();
        for (PortalTypeVO portalTypeVO : portalTypeVOS) {
            logger.info("哈哈portalTypeVO:" + portalTypeVO);
        }
    }

    @Autowired
    private DataSource dataSource;
    private Logger logger = LoggerFactory.getLogger(MainClassTest.class);

    @Autowired
    private MemberPOMapper memberPOMapper;


    @Test
    public void ConnectionTest() throws SQLException {
        logger.debug(dataSource.getConnection().toString());
        System.out.println("*************************");
        System.out.println(dataSource);

    }

    @Test
    public void memberPOMapperTest() {
        memberPOMapper.insert(new MemberPO(null, "lxf", "123123", null, null, null, null, null, null, null));
    }
    @Autowired
    private ProjectService projectService;

    @Test
    public void getProjectPOTest(){
        ProjectPO projectPO = projectPOMapper.selectByPrimaryKey(25);
        System.out.println(projectPO);
        System.out.println("*****************");
        ProjectPO projectPOById = projectService.getProjectPOById(25);
        System.out.println(projectPOById);
    }
    @Test
    public void getDetailProjectVOTest(){
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVOById(26);
        System.out.println(detailProjectVO);
    }
    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;
    @Test
    public void getOrderProjectVOTestInMapper(){

        OrderProjectVO orderProjectVO = orderProjectPOMapper.selectOrderProjectVO(4,23);
        System.out.println(orderProjectVO);

    }
    @Autowired
    private OrderService orderService;
    @Test
    public void getOrderProjectVOTestInService(){

        OrderProjectVO orderProjectVO = orderService.getOrderProjectVO(4, 23);
        System.out.println(orderProjectVO);

    }

}
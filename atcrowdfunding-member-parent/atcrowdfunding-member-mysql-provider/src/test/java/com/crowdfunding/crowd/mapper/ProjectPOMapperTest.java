package com.crowdfunding.crowd.mapper;

import com.crowdfunding.crowd.entity.vo.PortalProjectVO;
import com.crowdfunding.crowd.entity.vo.PortalTypeVO;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-12 10:44
 **/
public class ProjectPOMapperTest {
    @Autowired
    private ProjectPOMapper projectPOMapper;
    private Logger logger = LoggerFactory.getLogger(ProjectPOMapperTest.class);
    // 要从ioc容器拿对象，就跌服务器启动，ioc容器创建，bean创建。
//    @Test
//    public void selectPortalTypeVOList() {
//        List<PortalTypeVO> portalTypeVOS = projectPOMapper.selectPortalTypeVOList();
//        for (PortalTypeVO portalTypeVO : portalTypeVOS) {
//            logger.debug("portalTypeVO:"+portalTypeVO);
//        }
//    }
}
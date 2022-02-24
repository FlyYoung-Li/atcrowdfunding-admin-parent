package com.crowdfunding.crowd.service.api;

import com.crowdfunding.crowd.entity.po.ProjectPO;
import com.crowdfunding.crowd.entity.vo.DetailProjectVO;
import com.crowdfunding.crowd.entity.vo.PortalTypeVO;
import com.crowdfunding.crowd.entity.vo.ProjectVO;

import java.util.List;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-06 09:57
 **/
public interface ProjectService {

    void addProjectPO(ProjectVO projectVO, Integer memberId);

    List<PortalTypeVO> getPortalTypeVOList();
    // 前面我们自己实现的，用不上
    ProjectPO getProjectPOById(Integer id);

    DetailProjectVO getDetailProjectVOById(Integer id);
}

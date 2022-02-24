package com.crowdfunding.crowd.handler;

import com.crowd.util.ResultEntity;
import com.crowdfunding.crowd.entity.po.ProjectPO;
import com.crowdfunding.crowd.entity.vo.DetailProjectVO;
import com.crowdfunding.crowd.entity.vo.PortalTypeVO;
import com.crowdfunding.crowd.entity.vo.ProjectVO;
import com.crowdfunding.crowd.service.api.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-06 09:57
 **/
@RestController
public class ProjectProviderHandler {
    @Autowired
    private ProjectService projectService;

    /**
     *  将传过来的projectVO和memberId存入projectPO对象中
     * @param projectVO
     * @param memberId
     * @return ResultEntity
     */
    @RequestMapping("/save/projectPO/remote")
    ResultEntity<String> saveProjectPORemote(
            @RequestBody ProjectVO projectVO,
            @RequestParam("memberId") Integer memberId){

        try {
            projectService.addProjectPO(projectVO,memberId);
        } catch (Exception exception) {
            exception.printStackTrace();
            return  ResultEntity.failed(exception.getMessage());
        }
        return ResultEntity.successWithoutData();
    }
    // 首页获取项目中分类数据
    @RequestMapping("/get/portalTypeVOList/remote")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeVOList(){

        List<PortalTypeVO> portalTypeVOList = null;
        try {
            portalTypeVOList = projectService.getPortalTypeVOList();
            return  ResultEntity.successWithData(portalTypeVOList);
        } catch (Exception exception) {

            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }
    }

    /**
     *  根据projectId获取项目
     * @param id
     * @return
     */
    @RequestMapping("/get/projectPO/remote")
    public ResultEntity<ProjectPO> getProjectPOById(@RequestParam("id")Integer id){

        try {
            ProjectPO projectPO = projectService.getProjectPOById(id);

            return ResultEntity.successWithData(projectPO);
        } catch (Exception exception) {
            exception.printStackTrace();

            return ResultEntity.failed(exception.getMessage());
        }
    }

    @RequestMapping("/get/detailProjectVO/remote")
    public ResultEntity<DetailProjectVO> getDetailProjectVOById(@RequestParam("id")Integer id){

        try {
            DetailProjectVO detailProjectVO = projectService.getDetailProjectVOById(id);

            return ResultEntity.successWithData(detailProjectVO);
        } catch (Exception exception) {

            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }

    }
}

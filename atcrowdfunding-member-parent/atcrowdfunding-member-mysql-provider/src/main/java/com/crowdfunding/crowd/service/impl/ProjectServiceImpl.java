package com.crowdfunding.crowd.service.impl;

import com.crowdfunding.crowd.entity.po.*;
import com.crowdfunding.crowd.entity.vo.*;
import com.crowdfunding.crowd.mapper.*;
import com.crowdfunding.crowd.service.api.ProjectService;
import com.mysql.cj.Session;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-06 09:57
 **/
@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    @Autowired(required = false)
    private ProjectPOMapper projectPOMapper;
    @Autowired(required = false)
    private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;
    @Autowired(required = false)
    private ProjectItemPicPOMapper projectItemPicPOMapper;
    @Autowired(required = false)
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;
    @Autowired(required = false)
    private ReturnPOMapper returnPOMapper;


    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void addProjectPO(ProjectVO projectVO, Integer memberId) {


//        一.保存ProjectPO对象
//        1.创建空的projectPO对象
        ProjectPO projectPO = new ProjectPO();
//        2.将projectVO的属性复制到projectPO中。将memberId设置进去。
        BeanUtils.copyProperties(projectVO, projectPO);
        projectPO.setMemberid(memberId);
//      设置创建时间createTime
        String createTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        projectPO.setCreatedate(createTime);
//      设置项目状态
        projectPO.setStatus(0);
//        3.保存projectPO对象。
//        保存的时候，获取自增主键projectId。需要到projectPOMapper.xml文件中进行相关设置
        projectPOMapper.insertSelective(projectPO);
//        4.获取projectPO的主键。直接就是在projectPO对象中。
        Integer projectPOId = projectPO.getId();

//        二、保存项目、分类的关联关系信息
        List<Integer> typeIdList = projectVO.getTypeIdList();
        projectPOMapper.insertProjectPOTypeRelationship(projectPOId, typeIdList);

//        三、保存项目、标签的关联关系信息
        List<Integer> tagIdList = projectVO.getTagIdList();
        projectPOMapper.insertProjectPOTagRelationship(projectPOId, tagIdList);

//        四、保存项目中详情图片路径信息
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        projectItemPicPOMapper.insertPathList(projectPOId, detailPicturePathList);

//        五、保存项目发起人信息
        MemberLaunchInfoVO memberLaunchInfoVO = projectVO.getMemberLaunchInfoVO();
        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
        if (memberLaunchInfoVO == null) {
            throw new RuntimeException("保存项目发起人信息失败");
        }
        // 转成PO对象，进行报错
        BeanUtils.copyProperties(memberLaunchInfoVO, memberLaunchInfoPO);
        memberLaunchInfoPO.setMemberid(memberId);

        // MemberLaunchInfoMapper进行保存
        memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);
//        六、保存项目回报信息
        // 从projectVO中取出returnVOList
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        // new projectPOList
        List<ReturnPO> returnPOList = new ArrayList<>();
        //将所有的VO对象转成PO对象，设置projectId
        for (ReturnVO returnVO : returnVOList) {

            ReturnPO returnPO = new ReturnPO();
            BeanUtils.copyProperties(returnVO, returnPO);
            returnPO.setProjectid(projectPOId);
            returnPOList.add(returnPO);

        }
        // 传入returnPOList，通过自己写的sql语句传多个对象
        returnPOMapper.insertReturnPOList(returnPOList);
//        七、保存项目确认信息
        // 准备memberConfirmInfoPO对象
        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
        // 从projectVO获取memberConfirmInfoVO对象
        MemberConfirmInfoVO confirmInfoVO = projectVO.getMemberConfirmInfoVO();
        // 相同属性转化，设置memberId
        BeanUtils.copyProperties(confirmInfoVO, memberConfirmInfoPO);
        memberConfirmInfoPO.setMemberid(memberId);
        // 存入数据库中
        memberConfirmInfoPOMapper.insert(memberConfirmInfoPO);
    }

    @Override
    public List<PortalTypeVO> getPortalTypeVOList() {

        List<PortalTypeVO> portalTypeVOList = projectPOMapper.selectPortalTypeVOList();

        return portalTypeVOList;
    }

    @Override
    public ProjectPO getProjectPOById(Integer id) {
        ProjectPO po = projectPOMapper.selectByPrimaryKey(id);

        return po;
    }

    @Override
    public DetailProjectVO getDetailProjectVOById(Integer id) {
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVOById(id);

        Integer status = detailProjectVO.getStatus();

        switch (status) {
            case 0:
                detailProjectVO.setStatusText("即将开始");
                break;
            case 1:
                detailProjectVO.setStatusText("众筹中");
                break;
            case 2:
                detailProjectVO.setStatusText("众筹成功");
                break;
            case 3:
                detailProjectVO.setStatusText("众筹失败");
                break;
            default:
                break;
        }
        try {
            // 指定SimpleDateFormat日期格式（String<-满足条件->Date）
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // 部署时间，在mysql数据库中，也是这样部署的
            String deployDate = detailProjectVO.getDeployDate();
            if(deployDate == null || deployDate.length() == 0){
                throw  new RuntimeException("项目没有部署日期，请联系服务人员，部署项目日期");
            }

            // 将指定格式的String类型的项目部署时间转成Date类型
            Date startDate = simpleDateFormat.parse(deployDate);
            // 当前时间的Date类型
            Date nowDate = new Date();
            // 两个Date类型获取时间戳，相减，毫秒数，转成天数
            int time = (int) ((nowDate.getTime() - startDate.getTime()) / 1000 / 60 / 60 / 24);
            // 项目众筹天数-已部署天数=截至多少天
            Integer day = detailProjectVO.getDay();
            Integer lastDay = day - time;

            detailProjectVO.setLastDay(lastDay);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return detailProjectVO;
    }
}

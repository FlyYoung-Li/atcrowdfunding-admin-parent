package com.crowdfunding.crowd.mapper;


import java.util.List;

import com.crowdfunding.crowd.entity.po.ProjectPO;
import com.crowdfunding.crowd.entity.po.ProjectPOExample;
import com.crowdfunding.crowd.entity.vo.DetailProjectVO;
import com.crowdfunding.crowd.entity.vo.DetailReturnVO;
import com.crowdfunding.crowd.entity.vo.PortalProjectVO;
import com.crowdfunding.crowd.entity.vo.PortalTypeVO;
import org.apache.ibatis.annotations.Param;

public interface ProjectPOMapper {
    long countByExample(ProjectPOExample example);

    int deleteByExample(ProjectPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectPO record);

    int insertSelective(ProjectPO record);

    List<ProjectPO> selectByExample(ProjectPOExample example);

    ProjectPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByExample(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByPrimaryKeySelective(ProjectPO record);

    int updateByPrimaryKey(ProjectPO record);

    void insertProjectPOTypeRelationship(@Param("projectPOId") Integer projectPOId, @Param("typeIdList") List<Integer> typeIdList);

    void insertProjectPOTagRelationship(@Param("projectPOId")Integer projectPOId, @Param("tagIdList") List<Integer> tagIdList);

    List<PortalTypeVO> selectPortalTypeVOList();
    //上一个方法的分步查询方法
//    List<PortalProjectVO> selectPortalProjectVOList(@Param("id")Integer id);

    DetailProjectVO selectDetailProjectVOById(@Param("id") Integer id);
    // 上一个方法的分步查询方法
//    List<String> selectDetailPicturePathList(@Param("id") Integer id);
//    List<DetailReturnVO> selectDetailReturnVOList(@Param("id") Integer id);
}
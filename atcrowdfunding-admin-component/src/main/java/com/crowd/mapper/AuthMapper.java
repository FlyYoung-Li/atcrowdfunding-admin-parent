package com.crowd.mapper;

import com.crowd.entity.Auth;
import com.crowd.entity.AuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthMapper {
    long countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    List<Integer> selectAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAssignAuthRelationship(@Param("roleId")Integer roleId, @Param("authIdList") List<Integer> authIdList);

    void deleteRoleAssignedAuthRelationship(Integer roleId);

    List<String> selectAssignedAuthNameListByLoginAcct(@Param("loginAcct")String loginAcct);
}
package com.wht.springcloud.fangchanwang.mapper;

import com.wht.springcloud.fangchanwang.model.CommunityModel;
import com.wht.springcloud.fangchanwang.model.CommunityModelExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityModelMapper {
    int countByExample(CommunityModelExample example);

    int deleteByExample(CommunityModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommunityModel record);

    int insertSelective(CommunityModel record);

    List<CommunityModel> selectByExample(CommunityModelExample example);

    CommunityModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommunityModel record, @Param("example") CommunityModelExample example);

    int updateByExample(@Param("record") CommunityModel record, @Param("example") CommunityModelExample example);

    int updateByPrimaryKeySelective(CommunityModel record);

    int updateByPrimaryKey(CommunityModel record);
}
package com.wht.springcloud.fangchanwang.mapper;

import com.wht.springcloud.fangchanwang.model.AgencyModel;
import com.wht.springcloud.fangchanwang.model.AgencyModelExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AgencyModelMapper {
    int countByExample(AgencyModelExample example);

    int deleteByExample(AgencyModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AgencyModel record);

    int insertSelective(AgencyModel record);

    List<AgencyModel> selectByExample(AgencyModelExample example);

    AgencyModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AgencyModel record, @Param("example") AgencyModelExample example);

    int updateByExample(@Param("record") AgencyModel record, @Param("example") AgencyModelExample example);

    int updateByPrimaryKeySelective(AgencyModel record);

    int updateByPrimaryKey(AgencyModel record);
}
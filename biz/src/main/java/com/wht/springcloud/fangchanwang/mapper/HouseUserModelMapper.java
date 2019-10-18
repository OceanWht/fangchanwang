package com.wht.springcloud.fangchanwang.mapper;

import com.wht.springcloud.fangchanwang.model.HouseUserModel;
import com.wht.springcloud.fangchanwang.model.HouseUserModelExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseUserModelMapper {
    int countByExample(HouseUserModelExample example);

    int deleteByExample(HouseUserModelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HouseUserModel record);

    int insertSelective(HouseUserModel record);

    List<HouseUserModel> selectByExample(HouseUserModelExample example);

    HouseUserModel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HouseUserModel record, @Param("example") HouseUserModelExample example);

    int updateByExample(@Param("record") HouseUserModel record, @Param("example") HouseUserModelExample example);

    int updateByPrimaryKeySelective(HouseUserModel record);

    int updateByPrimaryKey(HouseUserModel record);
}
package com.wht.springcloud.fangchanwang.mapper;

import com.wht.springcloud.fangchanwang.model.HouseMsgModel;
import com.wht.springcloud.fangchanwang.model.HouseMsgModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HouseMsgModelMapper {
    int countByExample(HouseMsgModelExample example);

    int deleteByExample(HouseMsgModelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HouseMsgModel record);

    int insertSelective(HouseMsgModel record);

    List<HouseMsgModel> selectByExample(HouseMsgModelExample example);

    HouseMsgModel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HouseMsgModel record, @Param("example") HouseMsgModelExample example);

    int updateByExample(@Param("record") HouseMsgModel record, @Param("example") HouseMsgModelExample example);

    int updateByPrimaryKeySelective(HouseMsgModel record);

    int updateByPrimaryKey(HouseMsgModel record);
}
package com.wht.springcloud.fangchanwang.mapper;

import com.wht.springcloud.fangchanwang.model.HouseModel;
import com.wht.springcloud.fangchanwang.model.HouseModelExample;
import com.wht.springcloud.fangchanwang.page.PageParams;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HouseModelMapper {
    int countByExample(HouseModelExample example);

    int deleteByExample(HouseModelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HouseModel record);

    int insertSelective(HouseModel record);

    List<HouseModel> selectByExample(HouseModelExample example);

    HouseModel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HouseModel record, @Param("example") HouseModelExample example);

    int updateByExample(@Param("record") HouseModel record, @Param("example") HouseModelExample example);

    int updateByPrimaryKeySelective(HouseModel record);

    int updateByPrimaryKey(HouseModel record);

    List<HouseModel> selectPageHouses(@Param("record") HouseModel houseModel, @Param("pageParams") PageParams pageParams );
}
package com.wht.springcloud.fangchanwang.mapper;

import com.wht.springcloud.fangchanwang.model.BlogModel;
import com.wht.springcloud.fangchanwang.model.BlogModelExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogModelMapper {
    int countByExample(BlogModelExample example);

    int deleteByExample(BlogModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BlogModel record);

    int insertSelective(BlogModel record);

    List<BlogModel> selectByExampleWithBLOBs(BlogModelExample example);

    List<BlogModel> selectByExample(BlogModelExample example);

    BlogModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BlogModel record, @Param("example") BlogModelExample example);

    int updateByExampleWithBLOBs(@Param("record") BlogModel record, @Param("example") BlogModelExample example);

    int updateByExample(@Param("record") BlogModel record, @Param("example") BlogModelExample example);

    int updateByPrimaryKeySelective(BlogModel record);

    int updateByPrimaryKeyWithBLOBs(BlogModel record);

    int updateByPrimaryKey(BlogModel record);
}
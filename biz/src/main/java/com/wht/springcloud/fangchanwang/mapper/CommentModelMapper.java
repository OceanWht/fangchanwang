package com.wht.springcloud.fangchanwang.mapper;

import com.wht.springcloud.fangchanwang.model.CommentModel;
import com.wht.springcloud.fangchanwang.model.CommentModelExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentModelMapper {
    int countByExample(CommentModelExample example);

    int deleteByExample(CommentModelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CommentModel record);

    int insertSelective(CommentModel record);

    List<CommentModel> selectByExample(CommentModelExample example);

    CommentModel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CommentModel record, @Param("example") CommentModelExample example);

    int updateByExample(@Param("record") CommentModel record, @Param("example") CommentModelExample example);

    int updateByPrimaryKeySelective(CommentModel record);

    int updateByPrimaryKey(CommentModel record);
}
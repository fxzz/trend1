package com.trend.mapper;

import com.trend.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
   void insertComment(Comment comment);
}

package com.trend.mapper;

import com.trend.domain.Keyword;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TimelineMapper {
    List<Keyword> selectKeywordBySearchKeyword(String keyword);
}

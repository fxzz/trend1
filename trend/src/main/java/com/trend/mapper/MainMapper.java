package com.trend.mapper;

import com.trend.domain.Posting;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainMapper {

    Integer countTodayData();
    Integer countOneMonthData();
    List<Posting> selectMainPostingPage();
}

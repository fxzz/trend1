package com.trend.mapper;

import com.trend.domain.Keyword;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CalendarMapper {

   List<String> getMainTodayData(String date);
   List<Keyword> search(Map map);
}

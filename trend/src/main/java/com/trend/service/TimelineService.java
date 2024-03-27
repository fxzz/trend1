package com.trend.service;

import com.trend.domain.Keyword;

import java.util.List;

public interface TimelineService {
   List<Keyword> findKeywordBySearchKeyword(String searchKeyword);

}

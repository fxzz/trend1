package com.trend.service;

import com.trend.domain.Keyword;
import com.trend.mapper.TimelineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TimelineServiceImpl implements TimelineService {

    private final TimelineMapper timelineMapper;


    @Override
    public List<Keyword> findKeywordBySearchKeyword(String searchKeyword) {
        return timelineMapper.selectKeywordBySearchKeyword(searchKeyword);
    }
}

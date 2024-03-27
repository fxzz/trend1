package com.trend.service;

import com.trend.domain.Posting;
import com.trend.mapper.MainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final MainMapper mainMapper;

    @Override
    public Integer countTodayData() {
        return mainMapper.countTodayData();
    }

    @Override
    public Integer countOneMonthData() {
        return mainMapper.countOneMonthData();
    }

    @Override
    public List<Posting> selectMainPostingPage() {
        return mainMapper.selectMainPostingPage();
    }
}

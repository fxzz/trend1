package com.trend.service;

import com.trend.domain.Posting;

import java.util.List;

public interface MainService {

    Integer countTodayData();
    Integer countOneMonthData();
    List<Posting> selectMainPostingPage();
}

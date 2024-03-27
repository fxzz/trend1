package com.trend.service;

import com.trend.domain.Keyword;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

public interface CalendarService {
    List<String> getMainTodayData(String date);

    List<Keyword> search(Map<String, Object> params);

    void generateExcel(Map<String, Object> params, HttpServletResponse response);
}

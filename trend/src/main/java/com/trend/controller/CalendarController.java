package com.trend.controller;


import com.trend.domain.Keyword;
import com.trend.service.CalendarService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;


    @GetMapping("/calendar")
    @ResponseBody
    public ResponseEntity getDataBySelectedDate(String date) {
        List<String> todayData = calendarService.getMainTodayData(date);
        if (todayData.isEmpty()) {
            return ResponseEntity.ok().body(""); //데이터가 없을때
        }
        return ResponseEntity.ok().body(todayData);
    }



    @GetMapping("detail-calendar")
    public String getDetailCalender() {
        return "calendar";
    }


    @GetMapping("/search")
    public String search(@RequestParam String type,
                         @RequestParam(required = false) String date,
                         @RequestParam(required = false) String startDate,
                         @RequestParam(required = false) String endDate,
                         Model model) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("date", date);
        params.put("startDate", startDate);
        params.put("endDate", endDate);



        List<Keyword> keywordList = calendarService.search(params);


        // 날짜별로 그룹화된 리스트를 만듭니다.
        Map<String, List<Keyword>> groupedKeywordList = new LinkedHashMap<>();
        for (Keyword keyword : keywordList) {
            String dateKey = keyword.getRegDate().toString();
            if (!groupedKeywordList.containsKey(dateKey)) {
                groupedKeywordList.put(dateKey, new ArrayList<>());
            }
            groupedKeywordList.get(dateKey).add(keyword);
        }

        // 모델에 추가합니다.
        model.addAttribute("groupedKeywordList", groupedKeywordList);

        return "calendar";
    }

    @GetMapping("/excel")
    public void generateExcel(@RequestParam String type,
                       @RequestParam String date,
                       HttpServletResponse response) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("date", date);

        // 파일 이름 설정
        String filename = date + ".xls";

        // 파일 다운로드를 위한 응답 헤더 설정
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);

        calendarService.generateExcel(params, response);


    }

    }

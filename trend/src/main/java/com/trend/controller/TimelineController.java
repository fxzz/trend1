package com.trend.controller;

import com.trend.domain.Keyword;
import com.trend.service.TimelineService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TimelineController {

    private final TimelineService timelineService;

    @GetMapping("/timeline")
    public String timelineSearchForm() {
        return "timeline";
    }

    @GetMapping("/timeline/search")
    public String timelineSearchSubmit(@RequestParam String searchKeyword, Model model) {
        if ("".equals(searchKeyword)) {
            return "timeline";
        }
        List<Keyword> keywordList = timelineService.findKeywordBySearchKeyword(searchKeyword);
        model.addAttribute("keywordList", keywordList);
        return "timeline";
    }

}

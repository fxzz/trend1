package com.trend.controller;

import com.trend.domain.Posting;
import com.trend.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/")
    public String index(Model model) {


        Integer todayDataCount = mainService.countTodayData();
        Integer OneMonthDataCount = mainService.countOneMonthData();

        List<Posting> postingList = mainService.selectMainPostingPage();


        model.addAttribute("postingList", postingList);


        model.addAttribute("todayDataCount", todayDataCount);
        model.addAttribute("OneMonthDataCount", OneMonthDataCount);


        return "index";
    }
}

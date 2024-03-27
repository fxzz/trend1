package com.trend.controller;

import com.trend.domain.Account;
import com.trend.domain.Comment;
import com.trend.security.auth.CurrentAccount;
import com.trend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    @ResponseBody
    public Map<String, String> commentSubmit(@RequestBody Comment comment, @CurrentAccount Account account, RedirectAttributes attributes) {
        commentService.saveComment(comment, account.getAccountId());
        return Map.of("RESULT", "SUCCESS");
    }
}

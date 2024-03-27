package com.trend.controller;

import com.trend.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @PostMapping("/contact")
    public String contactSubmit(String title, String content, String email, RedirectAttributes attributes) {

        contactService.mailSend(title, content, email);
        attributes.addFlashAttribute("message", "메일을 전송했습니다.");

        return "redirect:/contact";
    }
}

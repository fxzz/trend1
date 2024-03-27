package com.trend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MailSendException.class)
    public String handleMailSendException(MailSendException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("message", e.getMessage());
        log.error(e.getMessage(), e);
        return "redirect:/contact";
    }

    @ExceptionHandler(InvalidImageFileException.class)
    public String handleValidateImageFileException(InvalidImageFileException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute("message", e.getMessage());
        log.error(e.getMessage(), e);
        return "redirect:/";
    }
}

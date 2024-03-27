package com.trend.controller;

import com.trend.controller.form.SignUpForm;
import com.trend.controller.validator.SignUpFormValidator;
import com.trend.domain.Account;
import com.trend.security.auth.AccountDetailsService;
import com.trend.security.auth.CurrentAccount;
import com.trend.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountDetailsService accountDetailsService;
    private final AccountService accountService;
    private final SignUpFormValidator signUpFormValidator;

    @InitBinder("signUpForm")
    public void signUpInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }



    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "signUp";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid SignUpForm signUpForm, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("signUpForm", signUpForm);
            return "signUp";
        }

        if (!signUpForm.isAccountPwEqualsAccountPwCon()) {
            bindingResult.rejectValue("password", "", "비밀번호가 일치하지 않습니다.");
            return "signUp";
        } else {
            accountService.saveAccount(signUpForm);
            attributes.addFlashAttribute("message", "회원가입을 완료했습니다.");
            return "redirect:/login";
        }
    }

    @GetMapping("/profile")
    public String profileForm() {
        return "profile";
    }

    @PostMapping("/profile-image")
    @ResponseBody
    public String profileSubmit(@RequestParam("croppedImage") MultipartFile croppedImage, @CurrentAccount Account account) {

        accountService.updateProfileImage(croppedImage, account.getAccountId());
        sessionReset(account);
        return "redirect:/profile";
    }

    public void sessionReset(Account account) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(account.getRole().toString()));

        UserDetails userDetails = accountDetailsService.loadUserByUsername(account.getUsername());
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
    }
}

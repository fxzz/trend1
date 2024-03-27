package com.trend.controller.validator;


import com.trend.controller.form.SignUpForm;
import com.trend.mapper.AccountMapper;
import com.trend.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final AccountMapper accountMapper;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpForm signUpForm = (SignUpForm) target;

        if (accountMapper.selectUsernameCount(signUpForm.getUsername()) != 0) {
            errors.rejectValue("username", "", "사용중인 아이디 입니다.");
        }

        if (accountMapper.selectEmailCount(signUpForm.getEmail()) != 0) {
            errors.rejectValue("email", "", "사용중인 이메일 입니다.");
        }

        if (accountMapper.selectNicknameCount(signUpForm.getNickname()) != 0) {
            errors.rejectValue("nickname", "", "사용중인 닉네임 입니다.");
        }


    }
}



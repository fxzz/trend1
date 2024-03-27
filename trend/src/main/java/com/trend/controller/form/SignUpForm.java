package com.trend.controller.form;

import com.trend.domain.Account;
import com.trend.domain.AccountRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpForm {



    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$", message = "영문, 숫자만 입력 가능하며, 3자에서 20자까지 입력해주세요.")
    private String username;

    @NotBlank
    @Length(min = 3, max = 50, message = "3자 이상 입력해주세요.")
    private String password;

    @NotBlank
    private String passwordConfirm;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9]{2,20}$", message = "한글, 영문, 숫자만 입력 가능하며, 2자에서 20자까지 입력해주세요.")
    private String nickname;

    public boolean isAccountPwEqualsAccountPwCon() {
        return password.equals(passwordConfirm);
    }

    public Account toAccount() {
        return Account.builder()
                .username(this.getUsername())
                .password(this.getPassword())
                .email(this.getEmail())
                .nickname(this.getNickname())
                .role(AccountRole.USER)
                .build();
    }
}

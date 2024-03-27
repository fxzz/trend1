package com.trend.security.auth;


import com.trend.domain.Account;
import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;




@Getter
public class AccountDetails extends User {

    private Account account;

    public AccountDetails(Account account) {
        super(account.getUsername(), account.getPassword(), AuthorityUtils.createAuthorityList("ROLE_"+account.getRole().toString()));
        this.account = account;
    }


    public Integer getAccountId() {
        return account.getAccountId();
    }


    @Override
    public String getUsername() {
        return account.getUsername();
    }

    public String getNickname() {
        return account.getNickname();
    }

    public String getEmail() {
        return account.getEmail();
    }


}

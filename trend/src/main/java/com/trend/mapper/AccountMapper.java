package com.trend.mapper;


import com.trend.domain.Account;
import com.trend.domain.PageRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AccountMapper {
    void insertAccount(Account account);
    int selectUsernameCount(String username);
    int selectEmailCount(String email);
    int selectNicknameCount(String nickname);
    Account selectAccount(String username);
    void updateProfileImage(Map map);

    List<Account> selectAllAccount(PageRequest pageRequest);
    int accountCount();
}

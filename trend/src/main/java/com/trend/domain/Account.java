package com.trend.domain;



import lombok.*;
import org.apache.ibatis.type.Alias;

@Builder
@Alias("acc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Integer accountId;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private AccountRole role;
    private String profileImage;
}

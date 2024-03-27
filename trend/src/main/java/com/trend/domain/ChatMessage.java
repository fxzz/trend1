package com.trend.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@Alias("ChatMessage")
public class ChatMessage {


    private String uuid;
    private Integer accountId;
    private String nickname;
    private String message; //메세지
    private LocalDateTime timestamp; // 채팅 친 시간

    private LocalDateTime createdAt;


}

package com.trend.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;


@Getter
@Setter
@ToString
@Alias("ChatRoom")
public class ChatRoom {

    private String roomName; // 방제목 : product 에서는 제목
    private Integer sender; // 전달자 필드 : product 에서는 구매자
    private Integer receiver; // 받는 사람 필드 : product 에서는 판매자
    private String uuid;


    private String senderNickname;
    private String receiverNickname;

    public boolean checkSenderReceiverEquality() {
        return this.sender.equals(this.receiver);
    }
}

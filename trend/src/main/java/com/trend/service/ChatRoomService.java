package com.trend.service;

import com.trend.domain.ChatMessage;
import com.trend.domain.ChatRoom;

import java.util.List;

public interface ChatRoomService {
    void createRoom(ChatRoom chatRoom, Integer accountId);

    List<ChatRoom> myChatRoom(Integer accountId);

    List<ChatMessage> getChatList(String uuid);
}

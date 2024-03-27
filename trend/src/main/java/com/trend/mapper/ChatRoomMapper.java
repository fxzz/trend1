package com.trend.mapper;


import com.trend.domain.ChatMessage;
import com.trend.domain.ChatRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ChatRoomMapper {

    Integer selectCheckRoom(ChatRoom chatRoom);
    void insertChatRoom(ChatRoom chatRoom);
    List<ChatRoom> selectMyChatRoom(Integer accountId);
    void insertChat(ChatMessage chatMessage);
    List<ChatMessage> selectChat(String uuid);
}

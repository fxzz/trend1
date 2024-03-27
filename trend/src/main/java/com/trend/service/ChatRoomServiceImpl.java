package com.trend.service;

import com.trend.domain.ChatMessage;
import com.trend.domain.ChatRoom;
import com.trend.mapper.ChatRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomMapper chatRoomMapper;

    @Override
    public void createRoom(ChatRoom chatRoom, Integer accountId) {
        chatRoom.setSender(accountId);
        chatRoom.setUuid(UUID.randomUUID().toString());
        if (chatRoom.checkSenderReceiverEquality()) {
            return;
        }


        Integer count = chatRoomMapper.selectCheckRoom(chatRoom);
        // 방이 없을때 생성
        if (count == 0) {
            chatRoomMapper.insertChatRoom(chatRoom);
        }
    }

    @Override
    public List<ChatRoom> myChatRoom(Integer accountId) {
        return chatRoomMapper.selectMyChatRoom(accountId);
    }

    @Override
    public List<ChatMessage> getChatList(String uuid) {
        return chatRoomMapper.selectChat(uuid);
    }
}

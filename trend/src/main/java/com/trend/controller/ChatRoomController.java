package com.trend.controller;

import com.trend.domain.Account;
import com.trend.domain.ChatMessage;
import com.trend.domain.ChatRoom;
import com.trend.security.auth.CurrentAccount;
import com.trend.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    //채팅 리스트 화면
    @GetMapping("/chat-room")
    public String rooms(Model model, @CurrentAccount Account account) {
       List<ChatRoom> roomList = chatRoomService.myChatRoom(account.getAccountId());
       model.addAttribute("roomList", roomList);
       return "chat";
    }



    // 채팅방 생성
    @PostMapping("/room-create")
    public String createRoom(ChatRoom chatRoom, @CurrentAccount Account account) {
        chatRoomService.createRoom(chatRoom, account.getAccountId());
        return "redirect:/chat-room";
    }

    //채팅 가져옴
    @GetMapping("/chat/{uuid}")
    @ResponseBody
    public List<ChatMessage> chat(@PathVariable String uuid) {
        return chatRoomService.getChatList(uuid);
    }



}

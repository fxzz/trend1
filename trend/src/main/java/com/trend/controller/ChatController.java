package com.trend.controller;

import com.trend.domain.Account;
import com.trend.domain.ChatMessage;
import com.trend.mapper.ChatRoomMapper;
import com.trend.security.auth.AccountDetails;
import com.trend.security.auth.CurrentAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatRoomMapper chatRoomMapper;

    @MessageMapping("/chat/{uuid}") // /app/chat로 보내면 /queue/{uuid} 여기로 받는다는 뜻
    @SendTo("/queue/{uuid}")
    public ChatMessage handleChatMessage(@Payload ChatMessage message,  Principal principal) {

        if (principal instanceof Authentication) {
            Authentication authentication = (Authentication) principal;

            if (authentication.getPrincipal() instanceof AccountDetails) {
                AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();
                Account account = accountDetails.getAccount();


                message.setAccountId(account.getAccountId());
                message.setNickname(account.getNickname());
                message.setTimestamp(LocalDateTime.now());

            }
        }

        chatRoomMapper.insertChat(message);
        return message;
    }
}

package com.chathall.springchatserver.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat/send")
    @SendTo("topic/public")
    public String sendMessage(@Payload String message) {
        return message;
    }

    @MessageMapping("/chat/add-user")
    @SendTo("topic/public")
    public String addUser(@Payload String message, SimpMessageHeaderAccessor headerAccessor) {
        if (headerAccessor.getSessionAttributes() != null)
            headerAccessor.getSessionAttributes().put("username", message);
        return message;
    }
}

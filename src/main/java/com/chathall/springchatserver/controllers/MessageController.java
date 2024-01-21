package com.chathall.springchatserver.controllers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.MessageDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.mappers.MessageDTOMapper;
import com.chathall.springchatserver.models.Message;
import com.chathall.springchatserver.services.mongodb.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final MessageDTOMapper messageDTOMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping
    public ResponseEntity<MessageDTO> createHttp(@RequestBody MessageDTO messageDTO) {
        Message message = messageDTOMapper.toEntity(messageDTO);
        message = messageService.add(message);
        return ResponseEntity.status(201).body(messageDTOMapper.toDTO(message));
    }

    @MessageMapping("/message/add")
    public void createStomp(@Payload MessageDTO messageDTO) {
        Message message = messageDTOMapper.toEntity(messageDTO);
        message = messageService.add(message);
        simpMessagingTemplate.convertAndSend("/topic/public/" + message.getChatroom().getId(),
                ResponseEntity.status(201).body(messageDTOMapper.toDTO(message)));
    }

    @GetMapping
    public ResponseEntity<Slice<MessageDTO>> getByChatroomIdPageable(
            @RequestParam UUID chatroomId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        Slice<Message> messages;
        messages = messageService.getByChatroomIdPageable(chatroomId, page, size);
        Slice<MessageDTO> results = messages.map(messageDTOMapper::toDTO);
        return ResponseEntity.ok(results);
    }

    @MessageMapping("/chat/add-user")
    @SendTo("topic/public")
    public String addUser(@Payload String message, SimpMessageHeaderAccessor headerAccessor) {
        if (headerAccessor.getSessionAttributes() != null)
            headerAccessor.getSessionAttributes().put("username", message);
        return message;
    }
}

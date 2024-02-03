package com.chathall.springchatserver.controllers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.MessageDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.mappers.MessageDTOMapper;
import com.chathall.springchatserver.models.Message;
import com.chathall.springchatserver.services.mongodb.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final MessageDTOMapper messageDTOMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;
    @Value("${client.stomp.destination}")
    private String stompDestination;

    @MessageMapping("/message/add")
    public void createMessage(@Payload MessageDTO messageDTO) {
        Message message = messageDTOMapper.toEntity(messageDTO);
        message = messageService.add(message);
        simpMessagingTemplate.convertAndSend(stompDestination + "/message/add/" + message.getChatroom().getId(),
                ResponseEntity.status(201).body(messageDTOMapper.toDTO(message)));
    }

    @MessageMapping("/message/update")
    public void updateMessage(@Payload MessageDTO messageDTO) {
        Message message = messageDTOMapper.toEntity(messageDTO);
        messageService.updateMessage(message);
        simpMessagingTemplate.convertAndSend(stompDestination + "/message/update/" + message.getChatroom().getId(),
                ResponseEntity.status(201).body(messageDTOMapper.toDTO(message)));
    }

    @GetMapping
    public ResponseEntity<Slice<MessageDTO>> getByChatroomIdPageable(
            @RequestParam UUID chatroomId,
            @RequestParam LocalDateTime startDate,
            @RequestParam(required = false) Integer size) {
        Slice<Message> messages;
        messages = messageService.getByChatroomIdAndDateBefore(chatroomId, startDate, size);
        Slice<MessageDTO> results = messages.map(messageDTOMapper::toDTO);
        return ResponseEntity.ok(results);
    }

    @MessageMapping("/message/delete")
    public void deleteMessage(@Payload UUID id) {
        Message message = messageService.getById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatusCode.valueOf(404), "Message with id=" + id.toString() + " doesn't exist")
        );
        messageService.deleteMessage(id);
        simpMessagingTemplate.convertAndSend("/topic/public/" + message.getChatroom().getId(),
                ResponseEntity.status(201).body(messageDTOMapper.toDTO(message)));
    }
}

package com.chathall.springchatserver.controllers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.MessageDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.mappers.MessageDTOMapper;
import com.chathall.springchatserver.models.Message;
import com.chathall.springchatserver.services.mongodb.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final MessageDTOMapper messageDTOMapper;

    @PostMapping
    public ResponseEntity<Message> create(@RequestBody Message message) {
        return ResponseEntity.status(201).body(messageService.add(message));
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
}

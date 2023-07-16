package com.chathall.springchatserver.controllers;

import com.chathall.springchatserver.models.Message;
import com.chathall.springchatserver.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageRepository messageRepository;

    @PostMapping
    public ResponseEntity<Message> create(@RequestBody Message message) {
        return ResponseEntity.status(201).body(messageRepository.save(message));
    }
}

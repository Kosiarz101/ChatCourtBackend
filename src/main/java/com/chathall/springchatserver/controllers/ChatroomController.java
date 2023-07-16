package com.chathall.springchatserver.controllers;

import com.chathall.springchatserver.models.Chatroom;
import com.chathall.springchatserver.services.mongodb.ChatroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatroomController {

    private final ChatroomService chatroomService;

    @PostMapping
    public ResponseEntity<Chatroom> add(@RequestBody Chatroom chatroom) {
        chatroomService.add(chatroom);
        return ResponseEntity.status(201).body(chatroomService.getById(chatroom.getId()).get());
    }

    @GetMapping
    public ResponseEntity<List<Chatroom>> getAll() {
        List<Chatroom> chatrooms = chatroomService.getAll();
        return ResponseEntity.ok(chatrooms);
    }
}

package com.chathall.springchatserver.controllers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomDTO;
import com.chathall.springchatserver.dtos.mappers.ChatroomDTOMapper;
import com.chathall.springchatserver.models.Chatroom;
import com.chathall.springchatserver.services.mongodb.ChatroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatroomController {

    private final ChatroomService chatroomService;
    private final ChatroomDTOMapper chatroomDTOMapper;

    @PostMapping
    public ResponseEntity<Chatroom> add(@RequestBody Chatroom chatroom) {
        chatroomService.add(chatroom);
        return ResponseEntity.status(201).body(chatroomService.getById(chatroom.getId()).get());
    }

    @GetMapping
    public ResponseEntity<Slice<ChatroomDTO>> getAllPageable(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        Slice<Chatroom> chatrooms;
        if (page == null)
            page = 0;
        if (size == null)
            chatrooms = chatroomService.getAllPageable(page);
        else
            chatrooms = chatroomService.getAllPageable(page, size);
        Slice<ChatroomDTO> results = chatrooms.map(chatroomDTOMapper::toDTO);
        return ResponseEntity.ok(results);
    }
}

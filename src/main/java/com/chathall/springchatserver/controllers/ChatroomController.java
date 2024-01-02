package com.chathall.springchatserver.controllers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.mappers.ChatroomDTOMapper;
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
    public ResponseEntity<ChatroomDTO> add(@RequestBody ChatroomDTO chatroomDTO) {
        Chatroom chatroom = chatroomService.add(chatroomDTOMapper.toEntity(chatroomDTO));
        return ResponseEntity.status(201).body(chatroomDTOMapper.toDTO(chatroom));
    }

    @GetMapping
    public ResponseEntity<Slice<ChatroomDTO>> getAllPageable(@RequestParam(required = false) Integer page,
                                                             @RequestParam(required = false) Integer size) {
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

    @GetMapping(params = {"chatroomName", "categoryId"})
    public ResponseEntity<Slice<ChatroomDTO>> findByNameAndCategoryId(@RequestParam("chatroomName") String chatroomName,
                                                                     @RequestParam("categoryId") String categoryId,
                                                                     @RequestParam(required = false) Integer page,
                                                                     @RequestParam(required = false) Integer size) {
        Slice<Chatroom> chatrooms;
        if (page == null)
            page = 0;
        chatrooms = chatroomService.findByNameAndCategoryId(chatroomName, categoryId, page, size);
        Slice<ChatroomDTO> results = chatrooms.map(chatroomDTOMapper::toDTO);
        return ResponseEntity.ok(results);
    }

    @GetMapping(params = {"chatroomName"})
    public ResponseEntity<Slice<ChatroomDTO>> findByNameAndCategoryId(@RequestParam("chatroomName") String chatroomName,
                                                                      @RequestParam(required = false) Integer page,
                                                                      @RequestParam(required = false) Integer size) {
        Slice<Chatroom> chatrooms;
        if (page == null)
            page = 0;
        chatrooms = chatroomService.findByNameContains(chatroomName, page, size);
        Slice<ChatroomDTO> results = chatrooms.map(chatroomDTOMapper::toDTO);
        return ResponseEntity.ok(results);
    }
}

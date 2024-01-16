package com.chathall.springchatserver.controllers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomSearchDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.mappers.ChatroomDTOMapper;
import com.chathall.springchatserver.dtos.chatcourtfrontend.mappers.CycleAvoidingContext;
import com.chathall.springchatserver.models.Chatroom;
import com.chathall.springchatserver.services.mongodb.ChatroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatroomController {

    private final ChatroomService chatroomService;
    private final ChatroomDTOMapper chatroomDTOMapper;
    private final CycleAvoidingContext cycleAvoidingContext = new CycleAvoidingContext();

    @PostMapping
    public ResponseEntity<ChatroomDTO> add(@RequestBody ChatroomDTO chatroomDTO) {
        Chatroom chatroom = chatroomService.add(toEntity(chatroomDTO));
        return ResponseEntity.status(201).body(toDTO(chatroom));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatroomDTO> getById(@PathVariable UUID id) {
        Optional<Chatroom> chatroomOptional = chatroomService.getById(id);
        Chatroom chatroom = chatroomOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatusCode.valueOf(400), "User has been already added to this chatroom")
        );
        return ResponseEntity.status(201).body(toDTO(chatroom));
    }

    @GetMapping
    public ResponseEntity<Slice<ChatroomDTO>> getAllPageable(@RequestParam("userId") UUID userId,
                                                             @RequestParam(required = false) boolean includeMessages,
                                                             @RequestParam(required = false, defaultValue = "0") int page,
                                                             @RequestParam(required = false) Integer size) {
        Slice<Chatroom> chatrooms = chatroomService.findByUserIdPageable(userId, includeMessages, page, size);
        Slice<ChatroomDTO> results = chatrooms.map(this::toDTO);
        return ResponseEntity.ok(results);
    }

    @GetMapping(params = {"chatroomName", "categoryId"})
    public ResponseEntity<Slice<ChatroomSearchDTO>> findPublicByNameAndCategoryId(
            @RequestParam("chatroomName") String chatroomName,
            @RequestParam("categoryId") UUID categoryId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false) Integer size) {
        Slice<ChatroomSearchDTO> chatrooms = chatroomService.findByNameAndCategoryId(chatroomName, categoryId, page, size);
        return ResponseEntity.ok(chatrooms);
    }

    @GetMapping(params = {"chatroomName"})
    public ResponseEntity<Slice<ChatroomSearchDTO>> findPublicByName(@RequestParam("chatroomName") String chatroomName,
                                                               @RequestParam(required = false, defaultValue = "0") Integer page,
                                                               @RequestParam(required = false) Integer size) {
        Slice<ChatroomSearchDTO> chatrooms = chatroomService.findByNameContains(chatroomName, page, size);
        return ResponseEntity.ok(chatrooms);
    }

    private Chatroom toEntity(ChatroomDTO dto) {
        return chatroomDTOMapper.toEntity(dto, cycleAvoidingContext);
    }

    private ChatroomDTO toDTO(Chatroom chatroom) {
        return chatroomDTOMapper.toDTO(chatroom, cycleAvoidingContext);
    }
}

package com.chathall.springchatserver.controllers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomChatPanelDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomSearchDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.mappers.ChatroomChatPanelDTOMapper;
import com.chathall.springchatserver.dtos.chatcourtfrontend.mappers.ChatroomDTOMapper;
import com.chathall.springchatserver.dtos.chatcourtfrontend.mappers.ChatroomSearchDTOMapper;
import com.chathall.springchatserver.models.Chatroom;
import com.chathall.springchatserver.models.ChatroomChatPanel;
import com.chathall.springchatserver.models.ChatroomSearch;
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
    private final ChatroomChatPanelDTOMapper chatroomChatPanelDTOMapper;
    private final ChatroomSearchDTOMapper chatroomSearchDTOMapper;

    @PostMapping
    public ResponseEntity<ChatroomDTO> add(@RequestBody ChatroomDTO chatroomDTO) {
        Chatroom chatroom = chatroomService.add(toEntity(chatroomDTO));
        return ResponseEntity.status(201).body(toDTO(chatroom));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatroomDTO> getById(@PathVariable UUID id) {
        Optional<Chatroom> chatroomOptional = chatroomService.getById(id);
        Chatroom chatroom = chatroomOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatusCode.valueOf(404), "Chatroom has not been found")
        );
        return ResponseEntity.status(200).body(toDTO(chatroom));
    }

    @GetMapping(path = "/{id}", params = {"includeMessages", "includeUsers"})
    public ResponseEntity<ChatroomChatPanelDTO> getById(@PathVariable UUID id,
                                               @RequestParam(required = false, defaultValue = "false") boolean includeMessages,
                                               @RequestParam(required = false, defaultValue = "false") boolean includeUsers) {
        Optional<ChatroomChatPanel> chatroomOptional = chatroomService.getById(id, includeMessages, includeUsers);
        ChatroomChatPanel chatroomChatPanel = chatroomOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatusCode.valueOf(404), "Chatroom has not been found")
        );
        return ResponseEntity.status(200).body(toDTO(chatroomChatPanel));
    }

    @GetMapping
    public ResponseEntity<Slice<ChatroomChatPanelDTO>> getAllByUserIdPageable(@RequestParam("userId") UUID userId,
                                                                   @RequestParam(required = false, defaultValue = "false") boolean includeMessages,
                                                                   @RequestParam(required = false, defaultValue = "false") boolean includeUsers,
                                                                   @RequestParam(required = false, defaultValue = "0") int page,
                                                                   @RequestParam(required = false) Integer size) {
        Slice<ChatroomChatPanel> chatrooms = chatroomService.findByUserIdPageable(userId, includeMessages, includeUsers, page, size);
        Slice<ChatroomChatPanelDTO> results = chatrooms.map(this::toDTO);
        return ResponseEntity.ok(results);
    }

    @GetMapping(params = {"chatroomName", "categoryId"})
    public ResponseEntity<Slice<ChatroomSearchDTO>> findPublicByNameAndCategoryIdPageable(
            @RequestParam("chatroomName") String chatroomName,
            @RequestParam("categoryId") UUID categoryId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false) Integer size) {
        Slice<ChatroomSearch> chatrooms = chatroomService.findByNameAndCategoryId(chatroomName, categoryId, page, size);
        Slice<ChatroomSearchDTO> results = chatrooms.map(this::toDTO);
        return ResponseEntity.ok(results);
    }

    @GetMapping(params = {"chatroomName"})
    public ResponseEntity<Slice<ChatroomSearchDTO>> findPublicByNamePageable(@RequestParam("chatroomName") String chatroomName,
                                                               @RequestParam(required = false, defaultValue = "0") Integer page,
                                                               @RequestParam(required = false) Integer size) {
        Slice<ChatroomSearch> chatrooms = chatroomService.findByNameContains(chatroomName, page, size);
        Slice<ChatroomSearchDTO> results = chatrooms.map(this::toDTO);
        return ResponseEntity.ok(results);
    }

    private Chatroom toEntity(ChatroomDTO dto) {
        return chatroomDTOMapper.toEntity(dto);
    }

    private ChatroomDTO toDTO(Chatroom chatroom) {
        return chatroomDTOMapper.toDTO(chatroom);
    }

    private ChatroomChatPanelDTO toDTO(ChatroomChatPanel chatroomChatPanel) {
        return chatroomChatPanelDTOMapper.toDTO(chatroomChatPanel);
    }

    private ChatroomSearchDTO toDTO(ChatroomSearch chatroomSearch) {
        return chatroomSearchDTOMapper.toDTO(chatroomSearch);
    }
}

package com.chathall.springchatserver.controllers;

import com.chathall.springchatserver.mappers.app.MessageAppMapper;
import com.chathall.springchatserver.models.SliceResponse;
import com.chathall.springchatserver.models.api.request.MessageRequestDTO;
import com.chathall.springchatserver.models.api.response.MessageResponseDTO;
import com.chathall.springchatserver.models.app.Message;
import com.chathall.springchatserver.services.entity.MessageService;
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
    private final MessageAppMapper messageAppMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;
    @Value("${client.stomp.destination}")
    private String stompDestination;

    @MessageMapping("/message/add")
    public void createMessage(@Payload MessageRequestDTO messageRequestDTO) {
        Message message = messageAppMapper.toApp(messageRequestDTO);
        message = messageService.add(message);
        simpMessagingTemplate.convertAndSend(stompDestination + "/message/add/" + message.getChatroom().getId(),
                ResponseEntity.status(201).body(messageAppMapper.toDTO(message)));
    }

    @MessageMapping("/message/update")
    public void updateMessage(@Payload MessageRequestDTO messageRequestDTO) {
        Message message = messageAppMapper.toApp(messageRequestDTO);
        message = messageService.update(message);
        simpMessagingTemplate.convertAndSend(stompDestination + "/message/update/" + message.getChatroom().getId(),
                ResponseEntity.status(200).body(messageAppMapper.toDTO(message)));
    }

    @GetMapping
    public ResponseEntity<SliceResponse<MessageResponseDTO>> getByChatroomIdAndBeforeOrEqualCreationDate(
            @RequestParam UUID chatroomId,
            @RequestParam LocalDateTime endDate,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        Slice<Message> messages;
        messages = messageService
                .getByChatroomIdAndBeforeOrEqualCreationDate(chatroomId, endDate, page, size);
        Slice<MessageResponseDTO> results = messages.map(messageAppMapper::toDTO);
        return ResponseEntity.ok(SliceResponse.fromSlice(results));
    }

    @MessageMapping("/message/delete")
    public void deleteMessage(@Payload UUID id) {
        Message message = messageService.getById(id).orElseThrow(() ->
            new ResponseStatusException(
                    HttpStatusCode.valueOf(404), "Message with id = " + id.toString() + " doesn't exist")
        );
        messageService.delete(id);
        simpMessagingTemplate.convertAndSend(stompDestination + "/message/delete/" + message.getChatroom().getId(),
                ResponseEntity.status(204).body(new MessageResponseDTO().setId(id)));
    }
}

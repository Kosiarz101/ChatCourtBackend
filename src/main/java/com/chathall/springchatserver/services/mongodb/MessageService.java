package com.chathall.springchatserver.services.mongodb;

import com.chathall.springchatserver.models.Message;
import com.chathall.springchatserver.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final int DEFAULT_MESSAGE_SIZE = 20;

    public Message add(Message message) {
        message.setNewId();
        message.setCreationDate(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public Slice<Message> getByChatroomIdPageable(UUID chatroomId, Integer page, Integer size) {
        return messageRepository.findAllByChatroomIdOrderByCreationDate(
                chatroomId, PageRequest.of(setPage(page), setSize(size))
        );
    }

    private int setSize(Integer size) {
        return size == null ? DEFAULT_MESSAGE_SIZE : size;
    }

    private int setPage(Integer page) {
        return page == null ? 0 : page;
    }
}

package com.chathall.springchatserver.services.mongodb;

import com.chathall.springchatserver.models.Message;
import com.chathall.springchatserver.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {
    public static final int DEFAULT_MESSAGE_SIZE = 5;
    private final MessageRepository messageRepository;

    public Message add(Message message) {
        message.setNewId();
        LocalDateTime now = LocalDateTime.now();
        message.setCreationDate(now);
        message.setLastModifiedDate(now);
        return messageRepository.save(message);
    }

    public Slice<Message> getByChatroomIdAndDateBefore(UUID chatroomId, LocalDateTime startDate, Integer size) {
        size = setSize(size);
        List<Message> messagesFromDB = messageRepository
                .findByChatroomIdAndStartDateBeforeOrderByCreationDateDesc(chatroomId, startDate, size + 1);
        boolean hasNext = messagesFromDB.size() > size;
        List<Message> messages;
        if (hasNext) {
            // list is unmodifiable so it must be cloned
            messages = new ArrayList<>(messagesFromDB);
            messages.removeLast();
        } else
            messages = messagesFromDB;
        return new SliceImpl<>(messages, Pageable.unpaged(), hasNext);
    }

    public void updateMessage(Message message) {
        message.setLastModifiedDate(LocalDateTime.now());
        messageRepository.save(message);
    }

    public void deleteMessage(UUID id) {
        messageRepository.deleteById(id);
    }

    public Optional<Message> getById(UUID id) {
        return messageRepository.findById(id);
    }

    private int setSize(Integer size) {
        return size == null ? DEFAULT_MESSAGE_SIZE : size;
    }

    private int setPage(Integer page) {
        return page == null ? 0 : page;
    }
}

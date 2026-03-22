package com.chathall.springchatserver.services.db;

import com.chathall.springchatserver.models.mongodb.Message;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {

    Message add(Message message);

    Slice<Message> getByChatroomIdAndBeforeOrEqualCreationDate(UUID chatroomId, LocalDateTime date, Integer page, Integer size);

    void updateMessage(Message message);

    void deleteMessage(UUID id);

    Optional<Message> getById(UUID id);
}

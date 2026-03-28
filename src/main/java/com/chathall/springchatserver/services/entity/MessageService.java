package com.chathall.springchatserver.services.entity;

import com.chathall.springchatserver.models.app.Message;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {

    Message add(Message Message);

    Slice<Message> getByChatroomIdAndBeforeOrEqualCreationDate(UUID chatroomId, LocalDateTime date, Integer page, Integer size);

    Message update(Message Message);

    void delete(UUID id);

    Optional<Message> getById(UUID id);
}

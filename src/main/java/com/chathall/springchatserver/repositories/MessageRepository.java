package com.chathall.springchatserver.repositories;

import com.chathall.springchatserver.models.app.Message;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {

    Message create(Message message);

    Message update(Message message);

    void delete(UUID id);

    Optional<Message> getById(UUID id);

    Slice<Message> findByChatroomIdAndCreationDateLessThanEqual(UUID chatroomId,
                                                                LocalDateTime endDate,
                                                                int page, int size);
}

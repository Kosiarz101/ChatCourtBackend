package com.chathall.springchatserver.repositories;

import com.chathall.springchatserver.models.mongodb.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends MongoRepository<Message, UUID> {

    List<Message> findAllByOrderByCreationDate();

    Slice<Message> findByChatroomIdAndCreationDateLessThanEqual(UUID chatroomId,
                                                                LocalDateTime endDate,
                                                                Pageable pageable);
}

package com.chathall.springchatserver.repositories;

import com.chathall.springchatserver.models.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends MongoRepository<Message, UUID> {

    List<Message> findAllByOrderByCreationDate();
    Slice<Message> findAllByChatroomIdOrderByCreationDate(UUID chatroomId, Pageable pageable);
}

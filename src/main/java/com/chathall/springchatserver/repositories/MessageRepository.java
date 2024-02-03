package com.chathall.springchatserver.repositories;

import com.chathall.springchatserver.models.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends MongoRepository<Message, UUID> {

    List<Message> findAllByOrderByCreationDate();
    Slice<Message> findByChatroomIdOrderByCreationDateDesc(UUID chatroomId, Pageable pageable);
    @Aggregation(pipeline = {
            "{ $match: { $and: [{ 'chatroom': ?0 }, { 'creationDate': { $lt: ?1 } }] } }",
            "{ $sort: {'creationDate': -1} }",
            "{ $limit: ?2 }"
    })
    List<Message> findByChatroomIdAndStartDateBeforeOrderByCreationDateDesc(UUID chatroomId, LocalDateTime startDate, int numberOfDocuments);
}

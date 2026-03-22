package com.chathall.springchatserver.repositories;

import com.chathall.springchatserver.models.data.mongodb.ChatroomUserMongo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatroomUserRepository extends MongoRepository<ChatroomUserMongo, UUID> {

    boolean existsByUserIdAndChatroomId(UUID userId, UUID chatroomId);

    @Aggregation(pipeline = {
            "{$match: {'user': ?0 } }",
            "{$project: {'chatroom': 1}}",
    })
    Slice<UUID> findChatroomIdsByUserId(UUID userId, Pageable pageable);
}

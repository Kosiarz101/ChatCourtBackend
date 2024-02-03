package com.chathall.springchatserver.repositories;

import com.chathall.springchatserver.dtos.chatcourtfrontend.ChatroomUserFlatDTO;
import com.chathall.springchatserver.models.AppUser;
import com.chathall.springchatserver.models.Chatroom;
import com.chathall.springchatserver.models.ChatroomUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatroomUserRepository extends MongoRepository<ChatroomUser, UUID> {
    boolean existsByUserAndChatroom(AppUser user, Chatroom chatroom);
    Slice<ChatroomUserFlatDTO> findChatroomIdsByUserId(AppUser user, Pageable pageable);
    @Aggregation(pipeline = {
            "{$match: {'user': ?0 } }",
            "{$project: {'chatroom': 1}}",
    })
    Slice<UUID> findChatroomIdsByUserId(UUID userId, Pageable pageable);
}

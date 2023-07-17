package com.chathall.springchatserver.repositories;

import com.chathall.springchatserver.models.Chatroom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatroomRepository extends MongoRepository<Chatroom, UUID> {

    List<Chatroom> findAllByOrderByCreationDateDesc();
    Slice<Chatroom> findAllByOrderByCreationDateDesc(Pageable pageable);
    Optional<Chatroom> findByName(String name);
    boolean existsByName(String name);
}

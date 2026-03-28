package com.chathall.springchatserver.persistence.mongodb.repositories.spring;

import com.chathall.springchatserver.persistence.mongodb.models.MessageMongo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MessageSpringMongoRepository extends MongoRepository<MessageMongo, UUID> {

    List<MessageMongo> findAllByOrderByCreationDate();

    Slice<MessageMongo> findByChatroomIdAndCreationDateLessThanEqual(UUID chatroomId,
                                                                     LocalDateTime endDate,
                                                                     Pageable pageable);
}

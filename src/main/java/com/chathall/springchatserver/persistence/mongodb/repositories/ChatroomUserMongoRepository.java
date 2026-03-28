package com.chathall.springchatserver.persistence.mongodb.repositories;

import com.chathall.springchatserver.persistence.mongodb.mappers.ChatroomUserDataMapper;
import com.chathall.springchatserver.app.models.ChatroomUser;
import com.chathall.springchatserver.persistence.mongodb.models.ChatroomUserMongo;
import com.chathall.springchatserver.persistence.repositories.ChatroomUserRepository;
import com.chathall.springchatserver.persistence.mongodb.repositories.spring.ChatroomUserSpringMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatroomUserMongoRepository implements ChatroomUserRepository {

    private final ChatroomUserSpringMongoRepository chatroomUserRepository;
    private final ChatroomUserDataMapper chatroomUserDataMapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public ChatroomUser create(ChatroomUser chatroomUser) {
        var chatroomUserMongo = chatroomUserDataMapper.toEntity(chatroomUser);
        chatroomUserMongo = chatroomUserRepository.save(chatroomUserMongo);
        return chatroomUserDataMapper.toApp(chatroomUserMongo);
    }

    @Override
    public boolean existsByUserIdAndChatroomId(UUID userId, UUID chatroomId) {
        return chatroomUserRepository.existsByUserIdAndChatroomId(userId, chatroomId);
    }

    @Override
    public Optional<ChatroomUser> getById(UUID id) {
        AggregationOperation match = new MatchOperation(Criteria.where("_id").is(id));
        AggregationResults<ChatroomUserMongo> aggregationResults = mongoTemplate.aggregate(
                Aggregation.newAggregation(List.of(match)),
                "chatroomUser",
                ChatroomUserMongo.class);
        List<ChatroomUserMongo> results = aggregationResults.getMappedResults();
        if (results.isEmpty())
            return Optional.empty();
        ChatroomUserMongo chatroomUserMongo = results.getFirst();
        return Optional.of(chatroomUserDataMapper.toApp(chatroomUserMongo));
    }
}

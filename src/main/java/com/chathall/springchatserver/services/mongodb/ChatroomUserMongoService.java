package com.chathall.springchatserver.services.mongodb;

import com.chathall.springchatserver.mappers.data.ChatroomUserDataMapper;
import com.chathall.springchatserver.models.app.ChatroomUser;
import com.chathall.springchatserver.models.data.mongodb.ChatroomUserMongo;
import com.chathall.springchatserver.repositories.ChatroomUserRepository;
import com.chathall.springchatserver.services.db.ChatroomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatroomUserMongoService implements ChatroomUserService {

    private final ChatroomUserRepository chatroomUserRepository;
    private final ChatroomUserDataMapper chatroomUserDataMapper;
    private final MongoTemplate mongoTemplate;

    public ChatroomUser add(ChatroomUser chatroomUser) {
        chatroomUser.setNewId();
        LocalDateTime now = LocalDateTime.now();
        chatroomUser.setCreationDate(now);
        chatroomUser.setLastModifiedDate(now);
        if (chatroomUserRepository.existsByUserIdAndChatroomId(chatroomUser.getUser().getId(), chatroomUser.getChatroom().getId()))
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "User has been already added to this chatroom");

        var chatroomUserMongo = chatroomUserDataMapper.toEntity(chatroomUser);
        chatroomUserMongo = chatroomUserRepository.save(chatroomUserMongo);
        return chatroomUserDataMapper.toApp(chatroomUserMongo);
    }

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

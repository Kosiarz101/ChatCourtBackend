package com.chathall.springchatserver.services.mongodb;

import com.chathall.springchatserver.models.mongodb.ChatroomUser;
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
    private final MongoTemplate mongoTemplate;

    public ChatroomUser add(ChatroomUser chatroomUser) {
        chatroomUser.setNewId();
        LocalDateTime now = LocalDateTime.now();
        chatroomUser.setCreationDate(now);
        chatroomUser.setLastModifiedDate(now);
        if (chatroomUserRepository.existsByUserAndChatroom(chatroomUser.getUser(), chatroomUser.getChatroom()))
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "User has been already added to this chatroom");
        return chatroomUserRepository.save(chatroomUser);
    }

    public Optional<ChatroomUser> getById(UUID id) {
        AggregationOperation match = new MatchOperation(Criteria.where("_id").is(id));
        AggregationResults<ChatroomUser> aggregationResults = mongoTemplate.aggregate(Aggregation.newAggregation(List.of(match)),
                "chatroomUser", ChatroomUser.class);
        List<ChatroomUser> results = aggregationResults.getMappedResults();
        if (results.isEmpty())
            return Optional.empty();
        return Optional.of(results.getFirst());
    }
}

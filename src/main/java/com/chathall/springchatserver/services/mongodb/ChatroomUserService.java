package com.chathall.springchatserver.services.mongodb;

import com.chathall.springchatserver.models.ChatroomUser;
import com.chathall.springchatserver.repositories.ChatroomUserRepository;
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

@RequiredArgsConstructor
@Service
public class ChatroomUserService {

    private final ChatroomUserRepository chatroomUserRepository;
    private final MongoTemplate mongoTemplate;

    public ChatroomUser add(ChatroomUser chatroomUser) {
        chatroomUser.setNewId();
        chatroomUser.setCreationDate(LocalDateTime.now());
        if (chatroomUserRepository.existsByUserAndChatroom(chatroomUser.getUser(), chatroomUser.getChatroom()))
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "User has been already added to this chatroom");
        return chatroomUserRepository.save(chatroomUser);
    }

    public Optional<ChatroomUser> getById(UUID id) {
        AggregationOperation match = new MatchOperation(Criteria.where("_id").is(id));
        AggregationResults<ChatroomUser> aggregationResults = mongoTemplate.aggregate(Aggregation.newAggregation(List.of(match)),
                "chatroomUser", ChatroomUser.class);
        List<ChatroomUser> results = aggregationResults.getMappedResults();
        if (results.size() == 0)
            return Optional.empty();
        return Optional.of(results.get(0));
    }
}

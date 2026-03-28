package com.chathall.springchatserver.persistence.mongodb.repositories;

import com.chathall.springchatserver.persistence.mongodb.mappers.ChatroomDataMapper;
import com.chathall.springchatserver.app.models.Chatroom;
import com.chathall.springchatserver.app.models.ChatroomSearch;
import com.chathall.springchatserver.persistence.mongodb.models.ChatroomMongo;
import com.chathall.springchatserver.persistence.repositories.ChatroomRepository;
import com.chathall.springchatserver.persistence.mongodb.repositories.spring.ChatroomSpringMongoRepository;
import com.chathall.springchatserver.persistence.mongodb.repositories.spring.ChatroomUserSpringMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatroomMongoRepository implements ChatroomRepository {

    private final ChatroomSpringMongoRepository chatroomRepository;
    private final ChatroomUserSpringMongoRepository chatroomUserRepository;
    private final ChatroomDataMapper chatroomDataMapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public Chatroom create(Chatroom chatroom) {
        chatroom.setNewId();
        LocalDateTime now = LocalDateTime.now();
        chatroom.setCreationDate(now);
        chatroom.setLastModifiedDate(now);

        ChatroomMongo chatroomMongo = chatroomDataMapper.toEntity(chatroom);
        chatroomMongo = chatroomRepository.save(chatroomMongo);

        return chatroomDataMapper.toApp(chatroomMongo);
    }

    @Override
    public Slice<Chatroom> findAllByOrderByCreationDateDesc(int pageNumber, int pageSize) {
        Slice<ChatroomMongo> chatroomMongos = chatroomRepository
                .findAllByOrderByCreationDateDesc(PageRequest.of(pageNumber, pageSize));
        return chatroomMongos.map(chatroomDataMapper::toApp);
    }

    @Override
    public Optional<Chatroom> findById(UUID id) {
        var chatroomMongo = chatroomRepository.findById(id);
        return chatroomMongo.map(chatroomDataMapper::toApp);
    }

    @Override
    public Optional<Chatroom> findById(UUID id, boolean includeMessages, boolean includeChatroomUsers) {
        AggregationOperation match = new MatchOperation(Criteria.where("_id").is(id));
        List<AggregationOperation> pipeline = new ArrayList<>(List.of(match));

        if (includeMessages)
            pipeline.add(createMessagesLookup());
        if(includeChatroomUsers)
            pipeline.add(createChatroomUsersLookup());
        Aggregation agg = Aggregation.newAggregation(pipeline);

        AggregationResults<ChatroomMongo> results = mongoTemplate.aggregate(agg, "chatroom", ChatroomMongo.class);
        if (results.getMappedResults().isEmpty())
            return Optional.empty();
        ChatroomMongo chatroomMongo = results.getMappedResults().getFirst();
        return Optional.of(chatroomDataMapper.toApp(chatroomMongo));
    }

    @Override
    public Slice<Chatroom> findByUserId(UUID chatroomUserId, boolean includeMessages, boolean includeChatroomUsers,
                                        int page, int size) {
        Slice<UUID> chatroomUsers = chatroomUserRepository
                .findChatroomIdsByUserId(chatroomUserId, PageRequest.of(page, size + 1));
        List<UUID> chatroomIds = chatroomUsers.getContent().stream().toList();

        Criteria criteria = Criteria.where("_id").in(chatroomIds);
        AggregationOperation match = new MatchOperation(criteria);
        AggregationOperation sort = new SortOperation(Sort.by(Sort.Direction.DESC, "creationDate"));
        List<AggregationOperation> pipeline = new ArrayList<>(List.of(match, sort));

        if (includeMessages)
            pipeline.add(createMessagesLookup());
        if (includeChatroomUsers)
            pipeline.add(createChatroomUsersLookup());
        Aggregation agg = Aggregation.newAggregation(pipeline);

        AggregationResults<ChatroomMongo> aggregationResults = mongoTemplate.aggregate(agg, "chatroom", ChatroomMongo.class);
        List<ChatroomMongo> results = aggregationResults.getMappedResults();

        boolean hasNext = results.size() > size;
        if (hasNext) {
            results = new ArrayList<>(results);
            results.removeLast();
        }

        List<Chatroom> chatrooms = results.stream()
                .map(chatroomDataMapper::toApp)
                .toList();
        return new SliceImpl<>(chatrooms, PageRequest.of(page, size), hasNext);
    }

    @Override
    public Slice<ChatroomSearch> findAllPublicByNameAndCategory(String name, UUID categoryId, int page, int size) {
        return chatroomRepository
                .findAllPublicByNameAndCategory(name, categoryId, PageRequest.of(page, size));
    }

    @Override
    public Slice<ChatroomSearch> findAllPublicByName(String name, int page, int size) {
        return chatroomRepository.findAllPublicByName(name, PageRequest.of(page, size));
    }

    private AggregationOperation createMessagesLookup() {
        AggregationOperation sort = new SortOperation(Sort.by(Sort.Direction.DESC, "creationDate"));
        AggregationOperation messagesLimit = new LimitOperation(5); // change do default
        AggregationPipeline aggPipeline = new AggregationPipeline();
        aggPipeline.add(sort);
        aggPipeline.add(messagesLimit);
        return new LookupOperation("message", Fields.field("_id"), Fields.field("chatroom"),
                null, aggPipeline, Fields.field("messages"));
    }

    private AggregationOperation createChatroomUsersLookup() {
        AggregationOperation sort = new SortOperation(Sort.by(Sort.Direction.DESC, "creationDate"));
        AggregationPipeline aggPipeline = new AggregationPipeline();
        aggPipeline.add(sort);
        return new LookupOperation("chatroomUser", Fields.field("_id"), Fields.field("chatroom"),
                null, aggPipeline, Fields.field("users"));
    }
}

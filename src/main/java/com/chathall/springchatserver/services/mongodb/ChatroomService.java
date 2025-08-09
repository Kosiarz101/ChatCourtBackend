package com.chathall.springchatserver.services.mongodb;

import com.chathall.springchatserver.models.Chatroom;
import com.chathall.springchatserver.models.ChatroomChatPanel;
import com.chathall.springchatserver.models.ChatroomSearch;
import com.chathall.springchatserver.repositories.ChatroomRepository;
import com.chathall.springchatserver.repositories.ChatroomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatusCode;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ChatroomService {

    private final ChatroomRepository chatroomRepository;
    private final ChatroomUserRepository chatroomUserRepository;
    private final MongoTemplate mongoTemplate;
    private final int DEFAULT_CHATROOM_SIZE = 5;

    public Chatroom add(Chatroom chatroom) {
        if (chatroomRepository.existsById(chatroom.getId())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Resource with given id already exists");
        }
        chatroom.setNewId();
        chatroom.setCreationDate(LocalDateTime.now());
        return chatroomRepository.save(chatroom);
    }

    public Optional<Chatroom> getById(UUID id) {
        return chatroomRepository.findById(id);
    }

    public Optional<ChatroomChatPanel> getById(UUID id, boolean includeMessages, boolean includeChatroomUsers) {
        AggregationOperation match = new MatchOperation(Criteria.where("_id").is(id));
        List<AggregationOperation> pipeline = new ArrayList<>(List.of(match, createCategoryLookup(), createCategoryUnwind()));

        if (includeMessages)
            pipeline.add(createMessagesLookup());
        if(includeChatroomUsers)
            pipeline.add(createChatroomUsersLookup());
        Aggregation agg = Aggregation.newAggregation(pipeline);

        AggregationResults<ChatroomChatPanel> results = mongoTemplate.aggregate(agg, "chatroom", ChatroomChatPanel.class);
        if (results.getMappedResults().isEmpty())
            return Optional.empty();
        return Optional.of(results.getMappedResults().getFirst());
    }

//    public Slice<Chatroom> findByUserIdPageable(UUID chatroomUserId, boolean includeMessages, int page, @Nullable Integer size) {
//        size = size == null ? DEFAULT_CHATROOM_SIZE : size;
//        Slice<UUID> chatroomUsers = chatroomUserRepository.findChatroomIdsByUserId(chatroomUserId, PageRequest.of(page, size));
//        List<UUID> chatroomIds = chatroomUsers.getContent().stream().toList();
//        if (includeMessages)
//            return chatroomRepository.findAllByIdsOrderByCreationDateDescWithMessages(chatroomIds, MessageService.DEFAULT_MESSAGE_SIZE, PageRequest.of(page, size));
//        else
//            return chatroomRepository.findAllByOrderByCreationDateDesc(PageRequest.of(page, size));
//    }

    public Slice<ChatroomChatPanel> findByUserIdPageable(UUID chatroomUserId, boolean includeMessages, boolean includeChatroomUsers, int page, @Nullable Integer size) {
        size = size == null ? DEFAULT_CHATROOM_SIZE : size;
        Slice<UUID> chatroomUsers = chatroomUserRepository.findChatroomIdsByUserId(chatroomUserId, PageRequest.of(page, size + 1));
        List<UUID> chatroomIds = chatroomUsers.getContent().stream().toList();

        Criteria criteria = Criteria.where("_id").in(chatroomIds);
        AggregationOperation match = new MatchOperation(criteria);
        AggregationOperation sort = new SortOperation(Sort.by(Sort.Direction.DESC, "creationDate"));
        List<AggregationOperation> pipeline = new ArrayList<>(List.of(match, sort, createCategoryLookup(), createCategoryUnwind()));

        if (includeMessages)
            pipeline.add(createMessagesLookup());
        if(includeChatroomUsers)
            pipeline.add(createChatroomUsersLookup());
        Aggregation agg = Aggregation.newAggregation(pipeline);

        AggregationResults<ChatroomChatPanel> aggregationResults = mongoTemplate.aggregate(agg, "chatroom", ChatroomChatPanel.class);
        List<ChatroomChatPanel> results = aggregationResults.getMappedResults();

        boolean hasNext = results.size() > size;
        if (hasNext) {
            results = new ArrayList<>(results);
            results.removeLast();
        }
        return new SliceImpl<>(results, PageRequest.of(page, size), hasNext);
    }

    public Slice<ChatroomSearch> findByNameAndCategoryId(String name, UUID categoryId, int page, @Nullable Integer size) {
        int pageSize = size == null ? DEFAULT_CHATROOM_SIZE : size;
        return chatroomRepository.findAllPublicByNameAndCategory(name, categoryId, PageRequest.of(page, pageSize));
    }

    public Slice<ChatroomSearch> findByNameContains(String name, int page, @Nullable Integer size) {
        int pageSize = size == null ? DEFAULT_CHATROOM_SIZE : size;
        return chatroomRepository.findAllPublicByName(name, PageRequest.of(page, pageSize));
    }

    private AggregationOperation createMessagesLookup() {
        AggregationOperation sort = new SortOperation(Sort.by(Sort.Direction.DESC, "creationDate"));
        AggregationOperation messagesLimit = new LimitOperation(DEFAULT_CHATROOM_SIZE);
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

    private AggregationOperation createCategoryLookup() {
        return new LookupOperation(Fields.field("category"), Fields.field("category"), Fields.field("_id"),
                Fields.field("category"));
    }

    private AggregationOperation createCategoryUnwind() {
        return new UnwindOperation(Fields.field("category"));
    }
}

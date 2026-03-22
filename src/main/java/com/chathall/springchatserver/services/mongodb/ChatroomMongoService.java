package com.chathall.springchatserver.services.mongodb;

import com.chathall.springchatserver.models.mongodb.Chatroom;
import com.chathall.springchatserver.models.ChatroomSearch;
import com.chathall.springchatserver.repositories.ChatroomRepository;
import com.chathall.springchatserver.repositories.ChatroomUserRepository;
import com.chathall.springchatserver.services.db.ChatroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatroomMongoService implements ChatroomService {

    private final int DEFAULT_CHATROOM_SIZE = 5;
    private final int DEFAULT_CHATROOM_PAGE = 0;

    private final ChatroomRepository chatroomRepository;
    private final ChatroomUserRepository chatroomUserRepository;
    private final MongoTemplate mongoTemplate;

    public Chatroom add(Chatroom chatroom) {
        chatroom.setNewId();
        LocalDateTime now = LocalDateTime.now();
        chatroom.setCreationDate(now);
        chatroom.setLastModifiedDate(now);
        return chatroomRepository.save(chatroom);
    }

    public Slice<Chatroom> findAll(@Nullable Integer pageNumber, @Nullable Integer pageSize) {
        return chatroomRepository.findAllByOrderByCreationDateDesc(PageRequest.of(getPage(pageNumber), getPageSize(pageSize)));
    }

    public Optional<Chatroom> findById(UUID id) {
        return chatroomRepository.findById(id);
    }

    public Optional<Chatroom> findById(UUID id, boolean includeMessages, boolean includeChatroomUsers) {
        AggregationOperation match = new MatchOperation(Criteria.where("_id").is(id));
        List<AggregationOperation> pipeline = new ArrayList<>(List.of(match));

        if (includeMessages)
            pipeline.add(createMessagesLookup());
        if(includeChatroomUsers)
            pipeline.add(createChatroomUsersLookup());
        Aggregation agg = Aggregation.newAggregation(pipeline);

        AggregationResults<Chatroom> results = mongoTemplate.aggregate(agg, "chatroom", Chatroom.class);
        if (results.getMappedResults().isEmpty())
            return Optional.empty();
        return Optional.of(results.getMappedResults().getFirst());
    }

    public Slice<Chatroom> findByUserId(UUID chatroomUserId, boolean includeMessages, boolean includeChatroomUsers,
                                        @Nullable Integer page, @Nullable Integer size) {
        page = getPage(page);
        size = getPageSize(size);
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

        AggregationResults<Chatroom> aggregationResults = mongoTemplate.aggregate(agg, "chatroom", Chatroom.class);
        List<Chatroom> results = aggregationResults.getMappedResults();

        boolean hasNext = results.size() > size;
        if (hasNext) {
            results = new ArrayList<>(results);
            results.removeLast();
        }
        return new SliceImpl<>(results, PageRequest.of(page, size), hasNext);
    }

    public Slice<ChatroomSearch> findByNameAndCategoryId(String name, UUID categoryId, @Nullable Integer page,
                                                         @Nullable Integer size) {
        return chatroomRepository
                .findAllPublicByNameAndCategory(name, categoryId, PageRequest.of(getPage(page), getPageSize(size)));
    }

    public Slice<ChatroomSearch> findByNameContains(String name, @Nullable Integer page, @Nullable Integer size) {
        return chatroomRepository.findAllPublicByName(name, PageRequest.of(getPage(page), getPageSize(size)));
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

    private int getPage(Integer requestPageNumber) {
        return requestPageNumber == null ? DEFAULT_CHATROOM_PAGE : requestPageNumber;
    }

    private int getPageSize(Integer requestPageSize) {
        return requestPageSize == null ? DEFAULT_CHATROOM_SIZE : requestPageSize;
    }
}

package com.chathall.springchatserver.repositories;

import com.chathall.springchatserver.models.Chatroom;
import com.chathall.springchatserver.models.ChatroomSearch;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatroomRepository extends MongoRepository<Chatroom, UUID> {

    List<Chatroom> findAllByOrderByCreationDateDesc();
    @Aggregation(pipeline = {
            "{$match: {\"_id\": { $in: ?0 } } }",
            "{$sort: {\"creationDate\": -1}}",
            "{$lookup: { from: \"message\", localField: \"_id\", foreignField: \"chatroom\", as: \"messages\", pipeline: [{$sort: { 'creationDate': -1 }}, { $limit: ?1 } ] }}",
    })
    Slice<Chatroom> findAllByIdsOrderByCreationDateDescWithMessages(List<UUID> chatroomIds, int numberOfMessages, Pageable pageable);
    Slice<Chatroom> findAllByOrderByCreationDateDesc(Pageable pageable);
    @Aggregation(pipeline =  {
            " {'$match': { $and: [{ name: {'$regex': /?0/, $options: 'i' } }, { 'category': ?1 }, { isPublic: true }] } }",
            " {'$lookup': { 'from': 'category', 'localField': 'category', 'foreignField': '_id', 'as': 'category' }}",
            " {'$unwind': '$category'}",
            " {'$lookup': { 'from': 'chatroomUser', 'localField': '_id', 'foreignField': 'chatroom', 'as': 'users' }}",
            """
            {"$addFields": {
                "userCount": {
                    $size: "$users"
                }
            }}"""
    })
    Slice<ChatroomSearch> findAllPublicByNameAndCategory(String name, UUID categoryId, Pageable pageable);
    @Aggregation(pipeline =  {
            " {'$match': { $and: [{ name: {'$regex': /?0/, $options: 'i' } }] } }",
            " {'$lookup': { 'from': 'category', 'localField': 'category', 'foreignField': '_id', 'as': 'category' }}",
            " {'$unwind': '$category'}",
            " {'$lookup': { 'from': 'chatroomUser', 'localField': '_id', 'foreignField': 'chatroom', 'as': 'users' }}",
            """
            {"$addFields": {
                "userCount": {
                    $size: "$users"
                }
            }}"""
    })
    Slice<ChatroomSearch> findAllPublicByName(String name, Pageable pageable);
    Slice<Chatroom> findAllByNameContainsIgnoreCaseAndIsPublicTrue(String name, Pageable pageable);
    boolean existsByName(String name);
}

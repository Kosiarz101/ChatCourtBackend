package com.chathall.springchatserver.persistence.mongodb.repositories.spring;

import com.chathall.springchatserver.app.models.ChatroomSearch;
import com.chathall.springchatserver.persistence.mongodb.models.ChatroomMongo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatroomSpringMongoRepository extends MongoRepository<ChatroomMongo, UUID> {

    Slice<ChatroomMongo> findAllByOrderByCreationDateDesc(Pageable pageable);

    @Aggregation(pipeline = {
            "{$match: {\"_id\": { $in: ?0 } } }",
            "{$sort: {\"creationDate\": -1}}",
            "{$lookup: { from: \"message\", localField: \"_id\", foreignField: \"chatroom\", as: \"messages\", pipeline: [{$sort: { 'creationDate': -1 }}, { $limit: ?1 } ] }}",
    })
    Slice<ChatroomMongo> findAllByIdsOrderByCreationDateDescWithMessages(List<UUID> chatroomIds, int numberOfMessages, Pageable pageable);

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

    Slice<ChatroomMongo> findAllByNameContainsIgnoreCaseAndIsPublicTrue(String name, Pageable pageable);

    boolean existsByName(String name);
}

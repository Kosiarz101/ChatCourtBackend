package com.chathall.springchatserver.persistence.mongodb.repositories.spring;

import com.chathall.springchatserver.persistence.mongodb.models.CategoryMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategorySpringMongoRepository extends MongoRepository<CategoryMongo, UUID> {
    boolean existsByName(String name);
}

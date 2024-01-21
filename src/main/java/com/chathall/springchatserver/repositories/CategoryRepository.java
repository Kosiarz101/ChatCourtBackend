package com.chathall.springchatserver.repositories;

import com.chathall.springchatserver.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends MongoRepository<Category, UUID> {
    boolean existsByName(String name);
}

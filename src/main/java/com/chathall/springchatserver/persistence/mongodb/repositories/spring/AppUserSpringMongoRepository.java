package com.chathall.springchatserver.persistence.mongodb.repositories.spring;

import com.chathall.springchatserver.persistence.mongodb.models.AppUserMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserSpringMongoRepository extends MongoRepository<AppUserMongo, UUID> {

    Optional<AppUserMongo> findByEmailIgnoreCase(String name);

    boolean existsByEmail(String name);
}

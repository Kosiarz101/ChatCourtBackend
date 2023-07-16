package com.chathall.springchatserver.repositories;

import com.chathall.springchatserver.models.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRepository extends MongoRepository<AppUser, UUID> {

    Optional<AppUser> findByEmail(String name);
    boolean existsByEmail(String name);
}

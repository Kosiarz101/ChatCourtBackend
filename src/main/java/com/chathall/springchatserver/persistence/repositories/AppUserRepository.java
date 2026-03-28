package com.chathall.springchatserver.persistence.repositories;

import com.chathall.springchatserver.app.models.AppUser;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository {
    AppUser create(AppUser appUser);

    Optional<AppUser> findById(UUID id);

    Optional<AppUser> findByEmailIgnoreCase(String email);

    boolean exists(String field, String value);
}

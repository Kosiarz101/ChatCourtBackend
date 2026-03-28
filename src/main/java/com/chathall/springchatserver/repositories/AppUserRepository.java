package com.chathall.springchatserver.repositories;

import com.chathall.springchatserver.models.app.AppUser;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository {
    AppUser create(AppUser appUser);

    Optional<AppUser> findById(UUID id);

    Optional<AppUser> findByEmailIgnoreCase(String email);

    boolean exists(String field, String value);
}

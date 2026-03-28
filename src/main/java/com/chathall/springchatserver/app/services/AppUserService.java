package com.chathall.springchatserver.app.services;

import com.chathall.springchatserver.app.models.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

public interface AppUserService extends UserDetailsService {

    AppUser create(AppUser AppUser);

    Optional<AppUser> getById(UUID id);

    Optional<AppUser> getByEmail(String email);

    boolean exists(String field, String value);
}

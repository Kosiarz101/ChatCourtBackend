package com.chathall.springchatserver.services.db;

import com.chathall.springchatserver.models.AppUser;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface AppUserService extends UserDetailsService {

    AppUser add(AppUser appUser);

    Optional<AppUser> getById(UUID id);

    Optional<AppUser> getByEmail(String email);

    Slice<AppUser> getBy(Map<String, Object> parameters, Integer page, Integer size);

    boolean exists(String field, String value);
}

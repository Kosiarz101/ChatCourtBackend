package com.chathall.springchatserver.services.mongodb;

import com.chathall.springchatserver.models.AppUser;
import com.chathall.springchatserver.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AppUserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final MongoTemplate mongoTemplate;

    public AppUser add(AppUser appUser) {
        appUser.setNewId();
        appUser.setCreationDate(LocalDateTime.now());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    public Optional<AppUser> getById(UUID id) {
        return appUserRepository.findById(id);
    }

    public Optional<AppUser> getByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    public Slice<AppUser> getBy(Map<String, Object> parameters, Integer page, Integer size) {
        Query query = new Query();
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.addCriteria(createCriteria(entry.getKey(), entry.getValue()));
        }
        throw new UnsupportedOperationException();
    }

    public boolean exists(String field, String value) {
        Query query = new Query();
        Criteria criteria = Criteria.where(field).is(value);
        query.addCriteria(criteria);
        query.limit(1);
        query.fields().include("email");
        return mongoTemplate.exists(query, AppUser.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> appUser = appUserRepository.findByEmail(email);
        if (appUser.isEmpty())
            throw new UsernameNotFoundException(email);
        return appUser.get();
    }

    private Criteria createCriteria(String field, Object value) {
        return Criteria.where(field).is(value);
    }
}

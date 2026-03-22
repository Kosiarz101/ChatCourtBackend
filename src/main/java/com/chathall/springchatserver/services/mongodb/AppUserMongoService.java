package com.chathall.springchatserver.services.mongodb;

import com.chathall.springchatserver.mappers.data.AppUserDataMapper;
import com.chathall.springchatserver.models.app.AppUser;
import com.chathall.springchatserver.models.data.mongodb.AppUserMongo;
import com.chathall.springchatserver.repositories.AppUserRepository;
import com.chathall.springchatserver.services.db.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserMongoService implements AppUserService {

    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final AppUserDataMapper appUserDataMapper;
    private final MongoTemplate mongoTemplate;

    public AppUser add(AppUser appUser) {
        appUser.setNewId();
        LocalDateTime now = LocalDateTime.now();
        appUser.setCreationDate(now);
        appUser.setLastModifiedDate(now);
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        var appUserMongo = appUserDataMapper.toEntity(appUser);
        appUserMongo = appUserRepository.save(appUserMongo);
        return appUserDataMapper.toApp(appUserMongo);
    }

    public Optional<AppUser> getById(UUID id) {
        return appUserRepository.findById(id)
                .map(appUserDataMapper::toApp);
    }

    public Optional<AppUser> getByEmail(String email) {
        return appUserRepository.findByEmailIgnoreCase(email)
                .map(appUserDataMapper::toApp);
    }

    public boolean exists(String field, String value) {
        Query query = new Query();
        Criteria criteria = Criteria.where(field).is(value);
        query.addCriteria(criteria);
        query.limit(1);
        query.fields().include("email");
        return mongoTemplate.exists(query, AppUserMongo.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUserMongo> appUserMongo = appUserRepository.findByEmailIgnoreCase(email);
        if (appUserMongo.isEmpty())
            throw new UsernameNotFoundException(email);
        return appUserDataMapper.toApp(appUserMongo.get());
    }
}

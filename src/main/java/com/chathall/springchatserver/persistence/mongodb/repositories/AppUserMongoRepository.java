package com.chathall.springchatserver.persistence.mongodb.repositories;

import com.chathall.springchatserver.persistence.mongodb.mappers.AppUserDataMapper;
import com.chathall.springchatserver.app.models.AppUser;
import com.chathall.springchatserver.persistence.mongodb.models.AppUserMongo;
import com.chathall.springchatserver.persistence.repositories.AppUserRepository;
import com.chathall.springchatserver.persistence.mongodb.repositories.spring.AppUserSpringMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserMongoRepository implements AppUserRepository {

    private final AppUserSpringMongoRepository appUserRepository;
    private final AppUserDataMapper appUserDataMapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public AppUser create(AppUser appUser) {
        var appUserMongo = appUserDataMapper.toEntity(appUser);
        appUserMongo = appUserRepository.save(appUserMongo);
        return appUserDataMapper.toApp(appUserMongo);
    }

    @Override
    public Optional<AppUser> findById(UUID id) {
        return appUserRepository.findById(id)
                .map(appUserDataMapper::toApp);
    }

    @Override
    public boolean exists(String field, String value) {
        Query query = new Query();
        Criteria criteria = Criteria.where(field).is(value);
        query.addCriteria(criteria);
        query.limit(1);
        query.fields().include("email");
        return mongoTemplate.exists(query, AppUserMongo.class);
    }

    @Override
    public Optional<AppUser> findByEmailIgnoreCase(String email) {
        return appUserRepository.findByEmailIgnoreCase(email)
                .map(appUserDataMapper::toApp);
    }
}

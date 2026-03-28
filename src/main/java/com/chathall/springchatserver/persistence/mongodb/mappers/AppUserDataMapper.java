package com.chathall.springchatserver.persistence.mongodb.mappers;

import com.chathall.springchatserver.app.models.AppUser;
import com.chathall.springchatserver.persistence.mongodb.models.AppUserMongo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AppUserDataMapper {

    @Mapping(target = "appUsername", source = "username")
    AppUser toApp(AppUserMongo appUserMongo);

    @Mapping(target = "username", source = "appUsername")
    AppUserMongo toEntity(AppUser appUser);
}

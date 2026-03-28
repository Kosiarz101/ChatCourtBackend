package com.chathall.springchatserver.mappers.data;

import com.chathall.springchatserver.models.app.AppUser;
import com.chathall.springchatserver.models.data.mongodb.AppUserMongo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AppUserDataMapper {

    @Mapping(target = "appUsername", source = "username")
    AppUser toApp(AppUserMongo appUserMongo);

    @Mapping(target = "username", source = "appUsername")
    AppUserMongo toEntity(AppUser appUser);
}

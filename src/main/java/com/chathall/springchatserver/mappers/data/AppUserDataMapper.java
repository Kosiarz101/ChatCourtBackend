package com.chathall.springchatserver.mappers.data;

import com.chathall.springchatserver.models.app.AppUser;
import com.chathall.springchatserver.models.data.mongodb.AppUserMongo;
import org.mapstruct.Mapper;

@Mapper
public interface AppUserDataMapper {

    AppUser toApp(AppUserMongo appUserMongo);

    AppUserMongo toEntity(AppUser appUser);
}

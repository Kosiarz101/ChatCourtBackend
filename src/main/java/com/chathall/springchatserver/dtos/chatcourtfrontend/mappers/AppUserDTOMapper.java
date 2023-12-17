package com.chathall.springchatserver.dtos.chatcourtfrontend.mappers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.AppUserDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.RegisterUserDTO;
import com.chathall.springchatserver.models.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface AppUserDTOMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "email", source = "username"),
            @Mapping(target = "username", source = "appUserUsername"),
            @Mapping(target = "creationDate", source = "creationDate")
    })
    AppUserDTO toDTO(AppUser appUser);

    @Mappings({
            @Mapping(target = "username", source = "email"),
            @Mapping(target = "appUserUsername", source = "username")
    })
    AppUser fromRegisterUser(RegisterUserDTO registerUserDTO);
}

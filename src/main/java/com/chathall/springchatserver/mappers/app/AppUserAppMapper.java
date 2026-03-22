package com.chathall.springchatserver.mappers.app;

import com.chathall.springchatserver.models.api.request.RegisterUserDTO;
import com.chathall.springchatserver.models.api.response.AppUserSimpleResponseDTO;
import com.chathall.springchatserver.models.app.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;

@Mapper(imports = {HashSet.class, GrantedAuthority.class})
public interface AppUserAppMapper {

    @Mappings({
            @Mapping(target = "username", source = "appUsername"),
    })
    AppUserSimpleResponseDTO toDTO(AppUser appUser);

    @Mappings({
            @Mapping(target = "appUsername", source = "username"),
    })
    AppUser fromRegisterUser(RegisterUserDTO registerUserDTO);
}

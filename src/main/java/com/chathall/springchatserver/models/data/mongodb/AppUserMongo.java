package com.chathall.springchatserver.models.data.mongodb;

import com.chathall.springchatserver.models.BaseModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@Accessors(chain = true)
@Document
public class AppUserMongo extends BaseModel implements UserDetails {
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String email;
    private String password;
    private String username;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public AppUserMongo setUsername(String email) {
        this.email = email;
        return this;
    }

    public String getAppUserUsername() {
        return username;
    }

    public AppUserMongo setAppUserUsername(String username) {
        this.username = username;
        return this;
    }
}

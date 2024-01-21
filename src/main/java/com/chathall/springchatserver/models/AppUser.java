package com.chathall.springchatserver.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@Document
public class AppUser extends BaseModel implements UserDetails {

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String email;
    private String password;
    private String username;
    @ReadOnlyProperty
    //@DocumentReference(lookup = "{ 'appUser':?#{#self._id} }")
    private Set<Message> messages;

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

    public AppUser setUsername(String email) {
        this.email = email;
        return this;
    }

    public String getAppUserUsername() {
        return username;
    }

    public AppUser setAppUserUsername(String username) {
        this.username = username;
        return this;
    }
}

package com.chathall.springchatserver.app.services.mainimpl;

import com.chathall.springchatserver.app.models.AppUser;
import com.chathall.springchatserver.app.services.AppUserService;
import com.chathall.springchatserver.persistence.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;

    public AppUser create(AppUser appUser) {
        appUser.setNewId();
        LocalDateTime now = LocalDateTime.now();
        appUser.setCreationDate(now);
        appUser.setLastModifiedDate(now);
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        return appUserRepository.create(appUser);
    }

    public Optional<AppUser> getById(UUID id) {
        return appUserRepository.findById(id);
    }

    public Optional<AppUser> getByEmail(String email) {
        return appUserRepository.findByEmailIgnoreCase(email);
    }

    public boolean exists(String field, String value) {
        return appUserRepository.exists(field, value);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> appUserMongo = appUserRepository.findByEmailIgnoreCase(email);
        if (appUserMongo.isEmpty())
            throw new UsernameNotFoundException(email);
        return appUserMongo.get();
    }
}

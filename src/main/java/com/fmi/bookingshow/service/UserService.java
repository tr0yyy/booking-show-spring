package com.fmi.bookingshow.service;

import com.fmi.bookingshow.component.JwtSecurity;
import com.fmi.bookingshow.constants.Role;
import com.fmi.bookingshow.exceptions.LoginFailedException;
import com.fmi.bookingshow.exceptions.RegistrationFailedException;
import com.fmi.bookingshow.model.UserEntity;
import com.fmi.bookingshow.model.UserSpecificsEntity;
import com.fmi.bookingshow.repository.UserRepository;
import com.fmi.bookingshow.repository.UserSpecificsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserSpecificsRepository userSpecificsRepository;
    private final JwtSecurity jwtSecurity;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserSpecificsRepository userSpecificsRepository, JwtSecurity jwtSecurity, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtSecurity = jwtSecurity;
        this.passwordEncoder = passwordEncoder;
        this.userSpecificsRepository = userSpecificsRepository;
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public void register(UserEntity userEntity) throws RegistrationFailedException {
        if (userRepository.findByUsername(userEntity.getUsername()).orElse(null) != null) {
            throw new RegistrationFailedException("User already exists");
        }
        UserSpecificsEntity specifics = userSpecificsRepository.save(new UserSpecificsEntity());
        UserEntity userToBeRegistered = new UserEntity(
                userEntity.getUsername(),
                passwordEncoder.encode(userEntity.getPassword()),
                userEntity.getEmail(),
                Role.USER,
                specifics
        );
        userRepository.save(userToBeRegistered);
    }

    public Map<UserEntity, String> login(UserEntity userEntity) throws LoginFailedException {
        UserEntity userFromDatabase = userRepository.findByUsername(userEntity.getUsername()).orElse(null);
        if (userFromDatabase == null) {
            throw new LoginFailedException("User does not exist");
        }
        if (!passwordEncoder.matches(userEntity.getPassword(), userFromDatabase.getPassword())) {
            throw new LoginFailedException("Password authentication failed");
        }
        return Collections.singletonMap(userFromDatabase, jwtSecurity.generateToken(userEntity));
    }
}

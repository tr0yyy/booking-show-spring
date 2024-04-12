package com.fmi.bookingshow.service;

import com.fmi.bookingshow.component.JwtSecurity;
import com.fmi.bookingshow.constants.Role;
import com.fmi.bookingshow.exceptions.LoginFailedException;
import com.fmi.bookingshow.exceptions.RegistrationFailedException;
import com.fmi.bookingshow.model.UserEntity;
import com.fmi.bookingshow.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtSecurity jwtSecurity;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JwtSecurity jwtSecurity, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtSecurity = jwtSecurity;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public void register(UserEntity userEntity) throws RegistrationFailedException {
        if (userRepository.findByUsername(userEntity.getUsername()).orElse(null) != null) {
            throw new RegistrationFailedException("User already exists");
        }
        UserEntity userToBeRegistered = new UserEntity(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                Role.USER
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
        return Collections.singletonMap(userEntity, jwtSecurity.generateToken(userEntity));
    }
}

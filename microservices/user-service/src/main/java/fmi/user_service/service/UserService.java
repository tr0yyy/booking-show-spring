package fmi.user_service.service;

import fmi.user_service.component.JwtSecurity;
import fmi.user_service.constants.Role;
import fmi.user_service.dto.user.UserSpecificsDto;
import fmi.user_service.exceptions.LoginFailedException;
import fmi.user_service.exceptions.OperationNotPermittedException;
import fmi.user_service.exceptions.RegistrationFailedException;
import fmi.user_service.mapper.UserMapper;
import fmi.user_service.model.UserEntity;
import fmi.user_service.model.UserSpecificsEntity;
import fmi.user_service.repository.UserRepository;
import fmi.user_service.repository.UserSpecificsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserSpecificsRepository userSpecificsRepository;
    private final JwtSecurity jwtSecurity;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserSpecificsRepository userSpecificsRepository, JwtSecurity jwtSecurity, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.jwtSecurity = jwtSecurity;
        this.passwordEncoder = passwordEncoder;
        this.userSpecificsRepository = userSpecificsRepository;
        this.userMapper = userMapper;
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public void register(UserEntity userEntity) throws RegistrationFailedException {
        if (userRepository.findByUsername(userEntity.getUsername()).orElse(null) != null) {
            throw new RegistrationFailedException("User already exists");
        }
        if (userRepository.findTop1ByOrderByUserIdAsc().isEmpty()) {
            userEntity.setRole(Role.ADMIN);
        } else {
            userEntity.setRole(Role.USER);
        }
        UserSpecificsEntity userSpecifics = new UserSpecificsEntity();
        userSpecifics.setRegistrationDate(new Date());
        UserSpecificsEntity specifics = userSpecificsRepository.save(userSpecifics);
        UserEntity userToBeRegistered = new UserEntity(
                userEntity.getUsername(),
                passwordEncoder.encode(userEntity.getPassword()),
                userEntity.getEmail(),
                userEntity.getRole(),
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
        return Collections.singletonMap(userFromDatabase, jwtSecurity.generateToken(userFromDatabase));
    }

    public UserSpecificsDto getUserSpecifics(UserEntity user) {
        return userMapper.userEntitytoUserSpecificsDto(user);
    }

    public UserSpecificsDto updateUserSpecifics(UserEntity user, UserSpecificsEntity userSpecifics) {
        user.getUserSpecifics().setBirthday(userSpecifics.getBirthday());
        user.getUserSpecifics().setBio(userSpecifics.getBio());
        user.getUserSpecifics().setPreferences(userSpecifics.getPreferences());
        userSpecificsRepository.save(user.getUserSpecifics());
        userRepository.save(user);
        return userMapper.userEntitytoUserSpecificsDto(user);
    }

    public void registerAdmin(UserEntity userEntity) throws OperationNotPermittedException {
        UserEntity user = userRepository.findByUsername(userEntity.getUsername()).orElse(null);
        if (user == null) {
            throw new OperationNotPermittedException("User not found");
        }
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }
}
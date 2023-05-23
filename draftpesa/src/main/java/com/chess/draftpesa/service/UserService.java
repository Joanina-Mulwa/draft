package com.chess.draftpesa.service;

import com.chess.draftpesa.domain.Enumarations.UserStatus;
import com.chess.draftpesa.domain.User;
import com.chess.draftpesa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User createUser(User user){
        log.info("Request to save user {} ", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUsername(getUsernameFromEmail(user.getEmail()));
        user.setCreatedAt(LocalDateTime.now().toString());
        user.setLastUpdatedAt(LocalDateTime.now().toString());
        return userRepository.save(user);
    }
    public String getUsernameFromEmail(String email){
        String firstPartOfEmail = email.split("@")[0].trim();
        if (!firstPartOfEmail.isEmpty()) {
            Optional<User> optionalUser = userRepository.findByUsername(firstPartOfEmail);
            if (optionalUser.isPresent()) {
                firstPartOfEmail = firstPartOfEmail + (10 + new Random().nextInt(89));
            }
            optionalUser = userRepository.findByUsername(firstPartOfEmail);
            if (optionalUser.isPresent()) {
                firstPartOfEmail = firstPartOfEmail + (100 + new Random().nextInt(899));
            }
        }
        return firstPartOfEmail;
    }

    public Optional<User> getById(Long id){
        log.info("Request to get user of ID {} ", id);
        return userRepository.findById(id);
    }

    public List<User> getAllUsers(){
        log.info("Request to get all users");
        return userRepository.findAll();
    }

    public User updateUserStatus(Long id, String userStatus) {
        log.info("Request to update status to {} for id {}",id, userStatus);
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setUserStatus(UserStatus.valueOf(userStatus));
            return userRepository.save(user);
        }
        return optionalUser.get();
    }
}

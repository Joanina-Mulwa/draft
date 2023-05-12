package com.chess.draftpesa.web.rest;

import com.chess.draftpesa.domain.User;
import com.chess.draftpesa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        log.info("REST request to create user {} ", user);
        return userService.createUser(user);
    }

    @GetMapping(path = "/{id}")
    public Optional<User> getUser(@PathVariable Long id){
        log.info("REST request to get user of ID {} ", id);
        return userService.getById(id);
    }

    @GetMapping
    public List<User> getAll(){
        log.info("REST request to get all users");
        return userService.getAllUsers();
    }
}

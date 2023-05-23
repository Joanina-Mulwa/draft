package com.chess.draftpesa.service;

import com.chess.draftpesa.config.ApplicationProperties;
import com.chess.draftpesa.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    private final ApplicationProperties applicationProperties;

    public CustomUserDetailsService(UserRepository userRepository, ApplicationProperties applicationProperties) {
        this.userRepository = userRepository;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.contains("@")) {
            return userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format(applicationProperties.getUserNotFoundMsg(), username)));
        }
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(applicationProperties.getUserNotFoundMsg(), username)));
    }
}

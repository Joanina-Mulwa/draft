package com.chess.draftpesa.security;

import com.chess.draftpesa.domain.Enumarations.UserStatus;
import com.chess.draftpesa.domain.User;
import com.chess.draftpesa.exceptions.UserNotActiveException;
import com.chess.draftpesa.exceptions.UserNotFoundException;
import com.chess.draftpesa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Slf4j
public class DraftAuthenticationManager implements AuthenticationManager {
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final String defaultAuthenticationErrorMessage = "Invalid username or password";

    public DraftAuthenticationManager(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        log.info("request authentication {}",username);

        User user = userService.findByUserName(username).orElseThrow(() -> new UserNotFoundException(defaultAuthenticationErrorMessage));
        validatePassword(password, user.getPassword());
        checkUserAccountStatus(user);

        List<GrantedAuthority> authorities = getUserAuthorities(user);
        return new DraftAuthentication(username, "", authorities).addUserDetails(user);
    }

    private List<GrantedAuthority> getUserAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (user.getUserRole() != null) {
            authorities.add(new SimpleGrantedAuthority(user.getUserRole().name()));
        }

        return authorities;
    }

    private void validatePassword(String inputPassword, String encodedPassword) {
        if (!passwordEncoder.matches(inputPassword, encodedPassword)) {
            throw new BadCredentialsException(defaultAuthenticationErrorMessage);
        }
    }

    private void checkUserAccountStatus(User user) {
        if (!user.isEnabled() || user.getUserStatus() == UserStatus.DISABLED) {
            String userNotActiveExceptionMessage = "User not active";
            throw new UserNotActiveException(userNotActiveExceptionMessage);
        }
    }
}

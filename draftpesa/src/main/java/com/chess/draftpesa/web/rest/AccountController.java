package com.chess.draftpesa.web.rest;

import com.chess.draftpesa.domain.User;
import com.chess.draftpesa.security.DraftAuthentication;
import com.chess.draftpesa.security.DraftAuthenticationManager;
import com.chess.draftpesa.security.JwtTokenUtil;
import com.chess.draftpesa.security.TokenProvider;
import com.chess.draftpesa.web.rest.vm.JWTResponseVM;
import com.chess.draftpesa.web.rest.vm.LoginVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final DraftAuthenticationManager draftAuthenticationManager;
    private final TokenProvider tokenProvider;

    public AccountController(DraftAuthenticationManager draftAuthenticationManager, TokenProvider tokenProvider) {
        this.draftAuthenticationManager = draftAuthenticationManager;
        this.tokenProvider = tokenProvider;
    }
    @PostMapping("/authenticate")
    public ResponseEntity<JWTResponseVM> authenticate(@RequestBody LoginVM loginVM) {
        log.info("Request to authenticate user: {}", loginVM);

        DraftAuthentication authentication = (DraftAuthentication) draftAuthenticationManager.authenticate(loginVM.toAuthenticationToken());

        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok().body(new JWTResponseVM(token, authentication.getName()));
    }
}

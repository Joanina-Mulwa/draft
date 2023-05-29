package com.chess.draftpesa.security;

import com.chess.draftpesa.config.ApplicationProperties;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "scopes";
    private static final String USER_ID = "cid";

    private final ApplicationProperties applicationProperties;

    public TokenProvider(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public String generateToken(DraftAuthentication authentication){
        final Claims claims = Jwts.claims().setSubject(String.valueOf(authentication.getPrincipal()));
        claims.put(AUTHORITIES_KEY, authentication.getAuthorities());
        claims.put(USER_ID, authentication.getUserId());

        long expiryTime = System.currentTimeMillis() + applicationProperties.getJwtValidityInSeconds() * 1000;
        return Jwts.builder().setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expiryTime))
                .signWith(SignatureAlgorithm.HS256, applicationProperties.getJwtSecret()).compact();
    }
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(applicationProperties.getJwtSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(applicationProperties.getJwtSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}

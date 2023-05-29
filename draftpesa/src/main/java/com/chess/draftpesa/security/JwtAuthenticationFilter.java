package com.chess.draftpesa.security;

import com.chess.draftpesa.config.ApplicationProperties;
import com.chess.draftpesa.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * NOTES:
 * What we do inside doFilterInternal():
 * – get JWT from the Authorization header (by removing Bearer prefix)
 * -> Implemented in parseJwtAuthToken method
 * – if the request has JWT, validate it, parse username from it
 * -> Get username from the jwt
 * – from username, Check if the username is not null and there is no existing authentication
 * then get UserDetails to create an Authentication object
 * – if the token is valid, set the current UserDetails in SecurityContext using setAuthentication(authentication) method.
 * <p>
 * After this, everytime you want to get UserDetails, just use SecurityContext like this:
 * <p>
 * UserDetails userDetails =
 * (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 * <p>
 * // userDetails.getUsername()
 * // userDetails.getPassword()
 * // userDetails.getAuthorities()
 */

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    ApplicationProperties applicationProperties;
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        try {
            String authToken = parseJwtAuthToken(request);
            if (authToken != null && !authToken.isEmpty()) {
                username = extractUsernameFromToken(authToken);
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                if(tokenProvider.validateJwtToken(authToken)){
                    final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwtAuthToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(applicationProperties.getTokenPrefix())) {
            return headerAuth.replace(applicationProperties.getTokenPrefix(), "");
        }

        return null;
    }

    private String extractUsernameFromToken(String jwtToken) {
        return tokenProvider.getUserNameFromJwtToken(jwtToken);
    }
}

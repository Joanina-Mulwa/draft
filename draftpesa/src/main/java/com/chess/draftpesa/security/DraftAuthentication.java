package com.chess.draftpesa.security;

import com.chess.draftpesa.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class DraftAuthentication extends UsernamePasswordAuthenticationToken {
    private String name;
    private String msisdn;
    private String email;
    private Long userId;
    public DraftAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
    public DraftAuthentication addUserDetails(User user) {
        this.setName(user.getFullName());
        this.setMsisdn(user.getMsisdn());
        this.setEmail(user.getEmail());
        this.setUserId(user.getId());
        return this;
    }

    @Override
    public String toString() {
        return "DraftAuthentication{" +
                "name='" + name + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", email='" + email + '\'' +
                "}"+super.toString();
    }
}

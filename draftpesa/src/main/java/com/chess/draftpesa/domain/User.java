package com.chess.draftpesa.domain;

import com.chess.draftpesa.domain.Enumarations.PlayerStatus;
import com.chess.draftpesa.domain.Enumarations.UserRole;
import com.chess.draftpesa.domain.Enumarations.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "tbl_users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String fullName;
    private String password;
    private String msisdn;
    private String email;
    private String username;
    private UserRole userRole;
    private UserStatus userStatus;
    private PlayerStatus playerStatus;
    private String createdBy;
    private String createdAt;
    private String lastUpdatedBy;
    private String lastUpdatedAt;
    private Boolean locked = false;
    private Boolean enabled = false;
    public String getFullName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(firstName);
        if(middleName != null){
            stringBuilder.append(" ").append(middleName);
        }
        if(lastName != null){
            stringBuilder.append(" ").append(lastName);
        }
        return stringBuilder.toString();
    }

    public String getCreatedBy(){
        return email;
    }
    public String getLastUpdatedBy(){
        return email;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(grantedAuthority);
 }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //TODO: this will support the already created users whose entries for enables are null. Remove later
        if(locked == null){
            return true;
        }
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(enabled == null){
            return true;
        }
        return enabled;
    }
}

package com.chess.draftpesa.domain;

import com.chess.draftpesa.domain.Enumarations.PlayerStatus;
import com.chess.draftpesa.domain.Enumarations.UserRole;
import com.chess.draftpesa.domain.Enumarations.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_users")
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String fullName;
    private String password;
    private String email;
    private String username;
    private UserRole userRole;
    private UserStatus userStatus;
    private PlayerStatus playerStatus;
    private String createdBy;
    private String createdAt;
    private String lastUpdatedBy;
    private String lastUpdatedAt;

    public User id(Long id) {
        this.id = id;
        return this;
    }

    public User firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public User fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public User password(String password) {
        this.password = password;
        return this;
    }

    public User email(String email) {
        this.email = email;
        return this;
    }

    public User username(String username) {
        this.username = username;
        return this;
    }

    public User userRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public User userStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public User playerStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
        return this;
    }

    public User createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public User lastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }
}

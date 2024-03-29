package com.chess.draftpesa.repository;

import com.chess.draftpesa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String firstPartOfEmail);

    Optional<User> findByEmail(String email);
}

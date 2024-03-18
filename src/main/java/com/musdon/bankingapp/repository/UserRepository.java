package com.musdon.bankingapp.repository;

import com.musdon.bankingapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByAccountNumber(String accountNumber);
}

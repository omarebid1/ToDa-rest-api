package com.userservice.repository;

import com.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByEmail(String email);

    Optional<User> findByEmail(String email);
}

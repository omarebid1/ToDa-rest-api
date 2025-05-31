package com.userservice.repository;

import com.userservice.entity.Otp;
import com.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    // Fetch latest OTP for a user
    Optional<Otp> findTopByUserOrderByExpirationTimeDesc(User user);

    // Fetch all unused OTPs for cleanup or marking as used
    List<Otp> findAllByUserAndUsedFalse(User user);
}

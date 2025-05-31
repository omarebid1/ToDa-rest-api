package com.userservice.service;

import com.userservice.dto.response.AuthResponse;
import com.userservice.entity.Otp;
import com.userservice.entity.User;
import com.userservice.exception.UserNotFoundEx;
import com.userservice.exception.WrongOtpEx;
import com.userservice.repository.OtpRepository;
import com.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${otp.length:6}")
    private int otpLength;

    @Value("${otp.expiration.minutes:5}")
    private int otpExpirationMinutes;

    /**
     * Generate and send a 6-digit OTP to the user's email
     */
    public AuthResponse generateAndSendOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundEx("Invalid request."));

        // Mark all previous OTPs as used
        otpRepository.findAllByUserAndUsedFalse(user)
                .forEach(o -> {
                    o.setUsed(true);
                    otpRepository.save(o);
                });

        String generatedOtp = String.format("%0" + otpLength + "d", new Random().nextInt((int) Math.pow(10, otpLength)));
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(otpExpirationMinutes);

        Otp otp = new Otp();
        otp.setOtp(generatedOtp);
        otp.setExpirationTime(expirationTime);
        otp.setUsed(false);
        otp.setUser(user);
        otpRepository.save(otp);

        emailService.sendOtpEmail(email, generatedOtp);

        log.info("OTP sent to {}", email);

        return new AuthResponse("OTP sent to " + email + " successfully!", LocalDateTime.now());
    }

    /**
     * Verify OTP and reset password in a single operation
     */
    @Transactional
    public AuthResponse verifyOtpAndResetPassword(String email, String inputOtp, String newPassword) {
        if (!isOtpValid(email, inputOtp)) {
            log.warn("Invalid or expired OTP attempt for {}", email);
            throw new WrongOtpEx("Invalid or expired OTP!");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundEx("user not found with email: " + email));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        log.info("Password reset successfully for {}", email);
        return new AuthResponse("Password reset successfully!", LocalDateTime.now());
    }

    /**
     * Validates the OTP: checks if it's correct, unused, and unexpired
     */
    public boolean isOtpValid(String email, String inputOtp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundEx("Invalid request."));

        Optional<Otp> latestOtp = otpRepository.findTopByUserOrderByExpirationTimeDesc(user);

        if (latestOtp.isEmpty()) return false;

        Otp otp = latestOtp.get();

        boolean isValid = !otp.isUsed()
                && !otp.getExpirationTime().isBefore(LocalDateTime.now())
                && otp.getOtp().equals(inputOtp);

        if (isValid) {
            otp.setUsed(true);
            otpRepository.save(otp);
            log.info("OTP validated and marked as used for {}", email);
            return true;
        }

        return false;
    }
}
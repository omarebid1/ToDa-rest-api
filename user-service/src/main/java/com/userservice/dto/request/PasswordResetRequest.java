package com.userservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request to reset password using email and OTP")
public class PasswordResetRequest {

    @Schema(description = "User's email address", example = "user@example.com", required = true)
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "One-Time Password sent to user's email", example = "123456", required = true)
    @NotBlank(message = "OTP must not be blank")
    private String otp;

    @Schema(description = "New password to be set for the user", example = "NewStrongPassword123!", required = true)
    @NotBlank(message = "New password must not be blank")
    private String newPassword;
}
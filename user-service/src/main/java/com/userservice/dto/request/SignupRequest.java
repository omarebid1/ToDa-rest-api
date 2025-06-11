package com.userservice.dto.request;

import com.userservice.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to register a new user")
public class SignupRequest {

    @Schema(description = "User ID (optional, usually auto-generated)", example = "1")
    private Long id;

    @Schema(description = "Username of the user", example = "john_doe", required = true)
    @NotBlank(message = "Username must not be blank")
    private String username;

    @Schema(description = "Email address of the user", example = "john@example.com", required = true)
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "Password for the new account", example = "SecurePass123!", required = true)
    @NotBlank(message = "Password must not be blank")
    private String password;

    @Schema(description = "Role of the user", example = "USER", required = true, enumAsRef = true)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role must not be null")
    private Role role;

    public SignupRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
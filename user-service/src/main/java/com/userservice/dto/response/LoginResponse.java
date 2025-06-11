package com.userservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response returned upon successful login")
public class LoginResponse {

    @Schema(description = "Login success message", example = "Login successful")
    private String message;

    @Schema(
            description = "Time of the response",
            example = "11-06-2025 16:10",
            type = "string",
            pattern = "dd-MM-yyyy HH:mm"
    )
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime timestamp;

    @Schema(description = "JWT authentication token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6...")
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}